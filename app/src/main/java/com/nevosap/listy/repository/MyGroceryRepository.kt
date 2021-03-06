package com.nevosap.listy.repository

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.nevosap.listy.database.DatabaseModule
import com.nevosap.listy.model.GroceryItemModel
import com.nevosap.listy.model.GroceryItemOrderModel
import com.nevosap.listy.model.GroceryListModel
import com.nevosap.listy.networking.FirebaseModule
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.HashMap

class MyGroceryRepository (private val listRepositoryListener: RepositoyListener<MutableList<GroceryListModel>>,private val itemsRepositoryListener: RepositoyListener<MutableList<GroceryItemModel>>):GroceryRepository {
    private val job =Job()
    private val uiScope = CoroutineScope(job+Dispatchers.Main)

    init{
        checkForItemUpdates()
        checkForListsUpdates()
    }

    override fun onClear() {
        uiScope.cancel()
    }

    override fun getItemsInStock() {
        uiScope.launch {
            withContext(Dispatchers.IO){
                loadStockFromLocalDb(itemsRepositoryListener)
            }
        }
    }
    private fun loadStockFromLocalDb(itemsRepositoryListener: RepositoyListener<MutableList<GroceryItemModel>>) {
        val items = DatabaseModule.groceryItemsDao.getItemsInStock()
        //for init
        itemsRepositoryListener.onSuccess(items)
    }
    private fun checkForItemUpdates(){
        //Items
        FirebaseModule.itemsRef.addChildEventListener(object: ChildEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                addOrUpdateStockInLocal(p0)
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                addOrUpdateStockInLocal(p0)
            }

            override fun onChildRemoved(p0: DataSnapshot) {
                TODO("Not yet implemented")
            }
        })
    }
    private fun checkForListsUpdates(){
        //lists
        FirebaseModule.listsRef.addChildEventListener(object : ChildEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                addOrUpdateListInLocal(p0)
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                addOrUpdateListInLocal(p0)
            }

            override fun onChildRemoved(p0: DataSnapshot) {
                deleteListInLocal(listFromSnapshot(p0))
            }
        })
    }

    private fun addOrUpdateListInLocal(p0: DataSnapshot) {
        val list = listFromSnapshot(p0)


        uiScope.launch {
            withContext(Dispatchers.IO) {
                DatabaseModule.groceryListsDao.addOrUpdateList(list)
                //todo opt
                val newLists = DatabaseModule.groceryListsDao.getAllLists()
                newLists.removeAll { !it.users.contains(FirebaseModule.user.uid) }
                listRepositoryListener.onSuccess( newLists)


            }
        }
    }

    private fun listFromSnapshot(p0: DataSnapshot): GroceryListModel {
        //todo extarct consts
        val id = p0.child("id").value.toString().toInt()
        val name = p0.child(("name")).value.toString()
        val timeInMils = p0.child("creationDate").child("time").value.toString().toLong()
        val creationDate = Date(timeInMils)
        val users = mutableListOf<String>()
        for (user in p0.child("users").children) {
            users.add(user.value.toString())
        }
        val orders = mutableListOf<GroceryItemOrderModel>()
        for (order in p0.child("orders").children) {
            val orderId = order.child("id").value.toString().toInt()
            val quantity = order.child("quantity").value.toString().toInt()
            val idItem = order.child("item").child("id").value.toString().toInt()
            val nameItem = order.child("item").child("name").value.toString()
            val priceItem = order.child("item").child("price").value.toString().toDouble()
            val item = GroceryItemModel(idItem, nameItem, priceItem)
            orders.add(GroceryItemOrderModel(orderId, item, quantity))
        }
        return GroceryListModel(id, name, creationDate, orders, users)
    }

    private fun addOrUpdateStockInLocal(p0: DataSnapshot):GroceryItemModel {
        val id = p0.key!!.toInt()
        val name = p0.child(FirebaseModule.ITEMS_NAME_PROPERTY).value.toString()
        val price = p0.child(FirebaseModule.ITEMS_PRICE_PROPERTY).value.toString().toDouble()
        val itemModel = GroceryItemModel(id, name, price)
        uiScope.launch {
            withContext(Dispatchers.IO) {
                DatabaseModule.groceryItemsDao.updateStock(mutableListOf(itemModel))
            }
        }
        return itemModel
    }

    override fun getAllLists() {
       uiScope.launch {
           withContext(Dispatchers.IO){
               val lists = DatabaseModule.groceryListsDao.getAllLists()
               //todo optimization
               lists.removeAll{
                   !it.users.contains(FirebaseModule.user.uid)
               }
               listRepositoryListener.onSuccess(lists)
           }
       }
    }

    override fun addOrUpdateList(groceryListModel: GroceryListModel) {
        //add user
        val user =FirebaseModule.user
        if(!groceryListModel.users.contains(user.uid)){
            groceryListModel.users.add(user.uid)
        }
        updateListInLocalDB(groceryListModel, listRepositoryListener)
    }

    private fun updateListInRemoteDB(groceryListModel: GroceryListModel) {
        FirebaseModule.listsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {
                val listKey = groceryListModel.id.toString()+groceryListModel.users[0]
                if (p0.hasChild(listKey)) {
                    //update list
                    val listValues = groceryListModel.toMap()
                    val childUpdates = HashMap<String, Any>()
                    childUpdates["/${listKey}"] = listValues
                    FirebaseModule.listsRef.updateChildren(childUpdates)
                } else {
                    //new list
                    FirebaseModule.listsRef.child(listKey)
                        .setValue(groceryListModel)
                }
            }
        })
    }

    private fun updateListInLocalDB(groceryListModel: GroceryListModel, listRepositoryListener: RepositoyListener<MutableList<GroceryListModel>>) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                val id =DatabaseModule.groceryListsDao.addOrUpdateList(groceryListModel)
                val lists =DatabaseModule.groceryListsDao.getAllLists()
                lists.removeAll{
                        !it.users.contains(FirebaseModule.user.uid)
                    }
                updateListInRemoteDB(GroceryListModel(id.toInt(),groceryListModel.name,groceryListModel.creationDate,groceryListModel.orders,groceryListModel.users))
                listRepositoryListener.onSuccess(lists)
            }
        }
    }

    override fun deleteList(groceryListModel: GroceryListModel) {
        deleteListInLocal(groceryListModel)
        deleteListInRemote(groceryListModel)
    }

    override fun shareList(key: String,context :Context) {
         val builder = FirebaseDynamicLinks.getInstance().createDynamicLink()
             .setLink(Uri.parse( "https://listyapp.page.link/?id=$key"))
             .setDomainUriPrefix("https://listyapp.page.link/?id=$key")

         val link = builder.buildDynamicLink()
         val sendIntent = Intent()
         val msg = "Hey, check this out: ${link.uri}"
         sendIntent.action = Intent.ACTION_SEND
         sendIntent.putExtra(Intent.EXTRA_TEXT, msg)
         sendIntent.type = "text/plain"
         context.startActivity(sendIntent)
    }

    override fun addSharedList(key: String) {
        FirebaseModule.listsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.hasChild(key)) {
                    val model = listFromSnapshot(p0.child(key))
                    model.users.add(FirebaseModule.user.uid)
                    val listValues = model.toMap()
                    val childUpdates = HashMap<String, Any>()
                    childUpdates["/$key"] = listValues
                    FirebaseModule.listsRef.updateChildren(childUpdates)
                    updateListInLocalDB(model, listRepositoryListener)
                }
            }
        })

    }

    private fun deleteListInLocal(groceryListModel: GroceryListModel) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                //todo delete in remote
                DatabaseModule.groceryListsDao.deleteList(groceryListModel)
                val newLists = DatabaseModule.groceryListsDao.getAllLists()
                newLists.removeAll {
                    !it.users.contains(FirebaseModule.user.uid)
                }
                listRepositoryListener.onSuccess(newLists)
            }
        }
    }
    private fun deleteListInRemote(groceryListModel: GroceryListModel) {
        val key = groceryListModel.id.toString()+groceryListModel.users[0]
        FirebaseModule.listsRef.child(key).removeValue()
    }
}