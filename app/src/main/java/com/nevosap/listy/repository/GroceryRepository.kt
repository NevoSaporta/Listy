package com.nevosap.listy.repository

import androidx.lifecycle.MutableLiveData
import com.nevosap.listy.model.GroceryItemModel
import com.nevosap.listy.model.GroceryListModel

interface GroceryRepository {
    fun getItemsInStock( itemsListener: Listener<MutableList<GroceryItemModel>>)

    fun getAllLists(listListener: Listener<MutableList<GroceryListModel>>)

    fun addOrUpdateList(listListener: Listener<MutableList<GroceryListModel>>,groceryListModel: GroceryListModel)
}