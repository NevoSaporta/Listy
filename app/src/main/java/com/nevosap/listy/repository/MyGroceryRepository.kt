package com.nevosap.listy.repository

import androidx.lifecycle.MutableLiveData
import com.nevosap.listy.model.GroceryItemModel
import com.nevosap.listy.model.GroceryListModel

class MyGroceryRepository private  constructor():GroceryRepository {
    companion object{
        val instance:MyGroceryRepository by lazy{
            MyGroceryRepository()
        }
    }
    override fun getItemsInStock(): MutableLiveData<MutableList<GroceryItemModel>> {
        TODO("Not yet implemented")
    }

    override fun getAllLists(): MutableLiveData<MutableList<GroceryListModel>> {
        TODO("Not yet implemented")
    }

    override fun addOrUpdateList(groceryListModel: GroceryListModel) {
        TODO("Not yet implemented")
    }
}