package com.nevosap.listy.repository

import com.nevosap.listy.database.DatabaseModule
import com.nevosap.listy.model.GroceryItemModel
import com.nevosap.listy.model.GroceryListModel
import kotlinx.coroutines.*

class MyGroceryRepository ():GroceryRepository {
    private val job =Job()
    private val uiScope = CoroutineScope(job+Dispatchers.Main)
    override fun onClear() {
        uiScope.cancel()
    }

    override fun getItemsInStock(itemsRepositoyListener: RepositoyListener<MutableList<GroceryItemModel>>) {
        uiScope.launch {
            withContext(Dispatchers.IO){
                val items =DatabaseModule.groceryItemsDao.getItemsInStock()
                if (items.isEmpty()){
                    val tmp =getTmpStock()
                    DatabaseModule.groceryItemsDao.updateStock(tmp)
                    itemsRepositoyListener.onSuccess(tmp)
                }else{
                    itemsRepositoyListener.onSuccess(items)
                }
            }
        }
    }

    override fun getAllLists(listRepositoyListener: RepositoyListener<MutableList<GroceryListModel>>) {
       uiScope.launch {
           withContext(Dispatchers.IO){
               val lists = DatabaseModule.groceryListsDao.getAllLists()
               listRepositoyListener.onSuccess(lists)
           }
       }
    }

    override fun addOrUpdateList(
        listRepositoyListener: RepositoyListener<MutableList<GroceryListModel>>,
        groceryListModel: GroceryListModel
    ) {
       uiScope.launch {
           withContext(Dispatchers.IO){
               DatabaseModule.groceryListsDao.addOrUpdateList(groceryListModel)
               listRepositoyListener.onSuccess(DatabaseModule.groceryListsDao.getAllLists())
           }
       }
    }

    override fun deleteList(
        listRepositoyListener: RepositoyListener<MutableList<GroceryListModel>>,
        groceryListModel: GroceryListModel
    ) {
        uiScope.launch {
            withContext(Dispatchers.IO){
                DatabaseModule.groceryListsDao.deleteList(groceryListModel)
                listRepositoyListener.onSuccess(DatabaseModule.groceryListsDao.getAllLists())
            }
        }
    }

    private fun getTmpStock ()= mutableListOf(
        GroceryItemModel(1,"milk",1.1),
        GroceryItemModel(2,"meat",2.1),
        GroceryItemModel(3,"water",3.1)
    )

}