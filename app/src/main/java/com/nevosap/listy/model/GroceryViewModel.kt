package com.nevosap.listy.model

import android.util.Log
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

    private val _navigateNew:MutableLiveData<Boolean>by lazy{
        MutableLiveData<Boolean>()
    }
    val navigateNew:LiveData<Boolean>
        get()=_navigateNew

    private val _navigateDetails:MutableLiveData<GroceryListModel> by lazy{
        MutableLiveData<GroceryListModel>()
    }
     val navigateDetails:MutableLiveData<GroceryListModel>
        get() = _navigateDetails

    private val _navigateEdit:MutableLiveData<Boolean> by lazy{
        MutableLiveData<Boolean>()
    }
    val navigateEdit:MutableLiveData<Boolean>
        get() = _navigateEdit

    private val _navigateHome:MutableLiveData<Boolean> by lazy{
        MutableLiveData<Boolean>()
    }
    val navigateHome:MutableLiveData<Boolean>
        get() = _navigateHome

    private val _editSavePressed:MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val editSavePressed:LiveData<Boolean>
        get() = _editSavePressed

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
        _navigateDetails.value =null
    }
    fun editListPressed(){
        _navigateEdit.value =true
    }
    fun navigateEditEnded(){
        _navigateEdit.value =false
    }
    fun editCancelPressed(){
        navigateHome()
    }
    fun editSavePressed(){
        _editSavePressed.value =true
    }
    fun errorSaving(){
        _editSavePressed.value =false
    }
    private fun navigateHome() {
        _navigateHome.value = true
    }

    fun navigateHomeEnded(){
        _navigateHome.value =false
    }
    fun addOrUpdateList(groceryListModel: GroceryListModel){
        val target =_allLists.value!!.firstOrNull{it.id == groceryListModel.id}
        if(null ==target ){
            //add
            _allLists.value!!.add(groceryListModel)
        }else{
            _allLists.value!!.remove(target)
            _allLists.value!!.add(groceryListModel)
        }
        _editSavePressed.value =false
        navigateHome()
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