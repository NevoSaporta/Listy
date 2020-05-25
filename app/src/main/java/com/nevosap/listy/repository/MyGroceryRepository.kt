package com.nevosap.listy.repository

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.nevosap.listy.database.DatabaseModule
import com.nevosap.listy.model.GroceryItemModel
import com.nevosap.listy.model.GroceryItemOrderModel
import com.nevosap.listy.model.GroceryListModel
import com.nevosap.listy.networking.FirebaseModule
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.HashMap

class MyGroceryRepository ():GroceryRepository {
    private val job =Job()
    private val uiScope = CoroutineScope(job+Dispatchers.Main)
    override fun onClear() {
        uiScope.cancel()
    }
    init {
        checkForUpdatesInRemote()
    }
    override fun getItemsInStock(itemsRepositoryListener: RepositoyListener<MutableList<GroceryItemModel>>) {
        uiScope.launch {
            withContext(Dispatchers.IO){
                loadStockFromLocalDb(itemsRepositoryListener)
            }
        }
    }
    private fun loadStockFromLocalDb(itemsRepositoryListener: RepositoyListener<MutableList<GroceryItemModel>>) {
        val items = DatabaseModule.groceryItemsDao.getItemsInStock()
        //for init
        if (items.count()==0) {
            items.addAll(loadStockFromRemoteDb())
        }
        itemsRepositoryListener.onSuccess(items)
    }
    private fun loadStockFromRemoteDb(): MutableList<GroceryItemModel> {
        val newItems = mutableListOf<GroceryItemModel>()
        FirebaseModule.listsRef.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }
            override fun onDataChange(p0: DataSnapshot) {
                for( child in p0.children){
                    newItems.add(addOrUpdateStockInLocal(p0))
                }
            }
        })
        return newItems
    }
    private fun checkForUpdatesInRemote(){
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
                TODO("Not yet implemented")
            }
        })
    }

    private fun addOrUpdateListInLocal(p0: DataSnapshot) {
        //todo extarct consts
        val id = p0.child("id").value.toString().toInt()
        val name =p0.child(("name")).value.toString()
        val timeInMils = p0.child("creationDate").child("time").value.toString().toLong()
        val creationDate = Date(timeInMils)
        val users = mutableListOf<String>()
        for (user in p0.child("users").children){
            users.add(user.value.toString())
        }
        val orders = mutableListOf<GroceryItemOrderModel>()
        for (order in p0.child("orders").children){
            val id = order.child("id").value.toString().toInt()
            val quantity = order.child("quantity").value.toString().toInt()
            val idItem =order.child("item").child("id").value.toString().toInt()
            val nameItem =order.child("item").child("name").value.toString()
            val priceItem =order.child("item").child("price").value.toString().toDouble()
            val item = GroceryItemModel(idItem,nameItem,priceItem)
            orders.add(GroceryItemOrderModel(id,item,quantity))
        }
        val list = GroceryListModel(id,name,creationDate,orders,users)
        uiScope.launch {
            withContext(Dispatchers.IO) {
                DatabaseModule.groceryListsDao.addOrUpdateList(list)
            }
        }
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

    override fun getAllLists(listRepositoryListener: RepositoyListener<MutableList<GroceryListModel>>) {
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

    override fun addOrUpdateList(
        listRepositoryListener: RepositoyListener<MutableList<GroceryListModel>>,
        groceryListModel: GroceryListModel
    ) {
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
                val listKey = groceryListModel.id.toString()+FirebaseModule.user.uid
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
                listRepositoryListener.onSuccess(lists)
                updateListInRemoteDB(GroceryListModel(id.toInt(),groceryListModel.name,groceryListModel.creationDate,groceryListModel.orders,groceryListModel.users))
            }
        }
    }

    override fun deleteList(
        listRepositoryListener: RepositoyListener<MutableList<GroceryListModel>>,
        groceryListModel: GroceryListModel
    ) {
        uiScope.launch {
            withContext(Dispatchers.IO){
                DatabaseModule.groceryListsDao.deleteList(groceryListModel)
                listRepositoryListener.onSuccess(DatabaseModule.groceryListsDao.getAllLists())
            }
        }
    }
}