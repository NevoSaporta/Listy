package com.nevosap.listy.repository

import com.nevosap.listy.model.GroceryItemModel
import com.nevosap.listy.model.GroceryListModel

interface GroceryRepository {
    fun onClear()

    fun getItemsInStock(itemsRepositoryListener: RepositoyListener<MutableList<GroceryItemModel>>)

    fun getAllLists(listRepositoryListener: RepositoyListener<MutableList<GroceryListModel>>)

    fun addOrUpdateList(listRepositoryListener: RepositoyListener<MutableList<GroceryListModel>>, groceryListModel: GroceryListModel)

    fun deleteList(listRepositoryListener: RepositoyListener<MutableList<GroceryListModel>>, groceryListModel: GroceryListModel)
}