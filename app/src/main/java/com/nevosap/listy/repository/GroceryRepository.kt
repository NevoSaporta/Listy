package com.nevosap.listy.repository

import com.nevosap.listy.model.GroceryItemModel
import com.nevosap.listy.model.GroceryListModel

interface GroceryRepository {
    fun onClear()

    fun getItemsInStock()

    fun getAllLists()

    fun addOrUpdateList(groceryListModel: GroceryListModel)

    fun deleteList(groceryListModel: GroceryListModel)
}