package com.nevosap.listy.repository

import com.nevosap.listy.model.GroceryItemModel
import com.nevosap.listy.model.GroceryListModel

interface GroceryRepository {
    fun onClear()

    fun getItemsInStock(itemsRepositoyListener: RepositoyListener<MutableList<GroceryItemModel>>)

    fun getAllLists(listRepositoyListener: RepositoyListener<MutableList<GroceryListModel>>)

    fun addOrUpdateList(listRepositoyListener: RepositoyListener<MutableList<GroceryListModel>>, groceryListModel: GroceryListModel)

    fun deleteList(listRepositoyListener: RepositoyListener<MutableList<GroceryListModel>>, groceryListModel: GroceryListModel)
}