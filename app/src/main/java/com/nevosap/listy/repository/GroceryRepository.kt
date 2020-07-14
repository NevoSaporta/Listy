package com.nevosap.listy.repository

import android.content.Context
import com.nevosap.listy.model.GroceryItemModel
import com.nevosap.listy.model.GroceryListModel

interface GroceryRepository {
    fun onClear()

    fun getItemsInStock()

    fun getAllLists()

    fun addOrUpdateList(groceryListModel: GroceryListModel)

    fun deleteList(groceryListModel: GroceryListModel)

    fun shareList(key: String,context : Context)

    fun addSharedList(key:String)
}