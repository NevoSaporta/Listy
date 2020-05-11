package com.nevosap.listy.repository

import com.nevosap.listy.database.DatabaseModule
import com.nevosap.listy.model.GroceryItemModel
import com.nevosap.listy.model.GroceryItemOrderModel
import com.nevosap.listy.model.GroceryListModel
import kotlinx.coroutines.*
import java.util.*

class MyGroceryRepository ():GroceryRepository {
    private val job =Job()
    private val uiScope = CoroutineScope(job+Dispatchers.Main)

    override fun getItemsInStock(itemsListener: Listener<MutableList<GroceryItemModel>>) {
        uiScope.launch {
            withContext(Dispatchers.IO){
                val items =DatabaseModule.groceryItemsDao.getItemsInStock()
                if (items.isEmpty()){
                    val tmp =getTmpStock()
                    DatabaseModule.groceryItemsDao.updateStock(tmp)
                    itemsListener.onSuccess(tmp)
                }else{
                    itemsListener.onSuccess(items)
                }
            }
        }
    }

    override fun getAllLists(listListener: Listener<MutableList<GroceryListModel>>) {
        //TODO("Not yet implemented")
    }

    override fun addOrUpdateList(
        listListener: Listener<MutableList<GroceryListModel>>,
        groceryListModel: GroceryListModel
    ) {
        //TODO("Not yet implemented")
    }
    private fun getTmpStock ()= mutableListOf(
        GroceryItemModel(1,"milk",1.1),
        GroceryItemModel(2,"meat",2.1),
        GroceryItemModel(3,"water",3.1)
    )

    private fun getTmpLists():MutableList<GroceryListModel>{
        val orders :MutableList<GroceryItemOrderModel> = getTmpStock().map{
            GroceryItemOrderModel(it.id,it,1)
        }.toMutableList()
        return mutableListOf(
            GroceryListModel(
                1,
                "temp",
                Date(System.currentTimeMillis()),
                orders
            )
        )
    }
}