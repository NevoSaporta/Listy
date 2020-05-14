package com.nevosap.listy.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.nevosap.listy.database.DatabaseModule
import com.nevosap.listy.model.GroceryItemModel
import com.nevosap.listy.model.GroceryListModel
import com.nevosap.listy.networking.FirebaseModule
import kotlinx.coroutines.*

class MyGroceryRepository ():GroceryRepository {
    private val job =Job()
    private val uiScope = CoroutineScope(job+Dispatchers.Main)
    override fun onClear() {
        uiScope.cancel()
    }

    override fun getItemsInStock(itemsRepositoryListener: RepositoyListener<MutableList<GroceryItemModel>>) {
        loadStockFromRemoteDb()
        uiScope.launch {
            withContext(Dispatchers.IO){
                loadStockFromLocalDb(itemsRepositoryListener)
            }
        }
    }
    private fun loadStockFromLocalDb(itemsRepositoryListener: RepositoyListener<MutableList<GroceryItemModel>>) {
        val items = DatabaseModule.groceryItemsDao.getItemsInStock()
        if (items.isNotEmpty()) {
            itemsRepositoryListener.onSuccess(items)
        }
    }
    private fun loadStockFromRemoteDb(){
        FirebaseModule.itemsRef.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {
                val items = mutableListOf<GroceryItemModel>()
                for(childItem in p0.children){
                    val id = childItem.key!!.toInt()
                    val name = childItem.child(FirebaseModule.ITEMS_NAME_PROPERTY).value.toString()
                    val price = childItem.child(FirebaseModule.ITEMS_PRICE_PROPERTY).value.toString().toDouble()
                    val itemModel =GroceryItemModel(id,name,price)
                    items.add(itemModel)
                }
                uiScope.launch {
                    withContext(Dispatchers.IO){
                        DatabaseModule.groceryItemsDao.updateStock(items)
                    }
                }
            }
        })
    }

    override fun getAllLists(listRepositoryListener: RepositoyListener<MutableList<GroceryListModel>>) {
       uiScope.launch {
           withContext(Dispatchers.IO){
               val lists = DatabaseModule.groceryListsDao.getAllLists()
               listRepositoryListener.onSuccess(lists)
           }
       }
    }

    override fun addOrUpdateList(
        listRepositoryListener: RepositoyListener<MutableList<GroceryListModel>>,
        groceryListModel: GroceryListModel
    ) {
       uiScope.launch {
           withContext(Dispatchers.IO){
               DatabaseModule.groceryListsDao.addOrUpdateList(groceryListModel)
               listRepositoryListener.onSuccess(DatabaseModule.groceryListsDao.getAllLists())
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