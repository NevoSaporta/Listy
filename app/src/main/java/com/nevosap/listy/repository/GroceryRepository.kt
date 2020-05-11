package com.nevosap.listy.repository

import androidx.lifecycle.MutableLiveData
import com.nevosap.listy.model.GroceryItemModel
import com.nevosap.listy.model.GroceryListModel

interface GroceryRepository {
    fun getItemsInStock()

    fun getAllLists()

    fun addOrUpdateList(groceryListModel: GroceryListModel)
}