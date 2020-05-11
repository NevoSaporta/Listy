package com.nevosap.listy.repository

import com.nevosap.listy.database.DatabaseModule
import com.nevosap.listy.model.GroceryItemModel
import com.nevosap.listy.model.GroceryItemOrderModel
import com.nevosap.listy.model.GroceryListModel
import kotlinx.coroutines.*
import java.util.*

class MyGroceryRepository private constructor():GroceryRepository {
    companion object{
        val instance:MyGroceryRepository by lazy{
            MyGroceryRepository()
        }
    }
    override fun getItemsInStock():MutableList<GroceryItemModel> {
        return getTmpStock()
    }

    override fun getAllLists(): MutableList<GroceryListModel> {
        //TODO("Not yet implemented")
        return getTmpLists()
    }

    override fun addOrUpdateList(groceryListModel: GroceryListModel) {
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