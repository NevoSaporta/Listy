package com.nevosap.listy.repository

import androidx.lifecycle.MutableLiveData
import com.nevosap.listy.model.GroceryItemModel
import com.nevosap.listy.model.GroceryListModel

interface GroceryRepository {
    fun getItemsInStock(): MutableLiveData<MutableList<GroceryItemModel>>

    fun getAllLists():MutableLiveData<MutableList<GroceryListModel>>

    fun addOrUpdateList(groceryListModel: GroceryListModel)
}