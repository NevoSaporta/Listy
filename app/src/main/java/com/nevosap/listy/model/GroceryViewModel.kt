package com.nevosap.listy.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class GroceryViewModel:ViewModel() {
    private val _itemsInStock :MutableLiveData<MutableList<GroceryItemModel>> by lazy{
        MutableLiveData<MutableList<GroceryItemModel>>().also {
            //TODO: loading from repository
            it.value =getTmpStock()
        }
    }
    val itemsInStock : LiveData<MutableList<GroceryItemModel>>
        get() = _itemsInStock

    private val _allLists : MutableLiveData<MutableList<GroceryListModel>> by lazy{
        MutableLiveData<MutableList<GroceryListModel>>().also {
            //TODO: loading from repository
            it.value = getTmpLists()
        }
    }
    val  allLists: LiveData<MutableList<GroceryListModel>>
        get() = _allLists

    private val _navigateNew:MutableLiveData<Boolean> =MutableLiveData<Boolean>().also {
        it.value =false
    }
    val navigateNew:LiveData<Boolean>
        get()=_navigateNew

    private val _navigateDetails:MutableLiveData<GroceryListModel> by lazy{
        MutableLiveData<GroceryListModel>()
    }
     val navigateDetails:MutableLiveData<GroceryListModel>
        get() = _navigateDetails

    fun newListPressed(){
        _navigateNew.value =true
    }
    fun navigateNewListEnded(){
        _navigateNew.value =false
    }

    fun groceryListPressed(groceryListModel: GroceryListModel){
        navigateDetails.value =groceryListModel
    }
    fun navigateDetailsEnded(){
        navigateDetails.value =null
    }
    private fun getTmpStock ()= mutableListOf(
        GroceryItemModel(1,"milk",1.1),
        GroceryItemModel(2,"meat",2.1),
        GroceryItemModel(3,"water",3.1)
    )
    private fun getTmpLists () = mutableListOf(
        GroceryListModel(
            1,
            "List1",
            creationDate = Date(System.currentTimeMillis()),
            orders = mutableListOf(
                GroceryItemOrderModel(
                    1,
                    GroceryItemModel(1,"milk",1.1),
                    5
                )
            )
        )
    )
}