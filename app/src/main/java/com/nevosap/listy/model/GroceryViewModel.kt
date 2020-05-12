package com.nevosap.listy.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nevosap.listy.repository.Listener
import com.nevosap.listy.repository.MyGroceryRepository

class GroceryViewModel:ViewModel() {
    private val repository = MyGroceryRepository()
    override fun onCleared() {
        super.onCleared()
        repository.onClear()
    }

    private val _itemsInStock :MutableLiveData<MutableList<GroceryItemModel>> by lazy{
        MutableLiveData<MutableList<GroceryItemModel>>().also {
            repository.getItemsInStock(object :Listener<MutableList<GroceryItemModel>>{
                override fun onSuccess(element: MutableList<GroceryItemModel>) {
                    _itemsInStock.postValue(element)
                }

                override fun onFailure(error: Throwable) {
                    TODO("Not yet implemented")
                }
            })
        }
    }
    val itemsInStock : LiveData<MutableList<GroceryItemModel>>
        get() = _itemsInStock

    private val _allLists : MutableLiveData<MutableList<GroceryListModel>> by lazy{
       MutableLiveData<MutableList<GroceryListModel>>().also {
           repository.getAllLists(object :Listener<MutableList<GroceryListModel>>{
               override fun onSuccess(element: MutableList<GroceryListModel>) {
                   _allLists.postValue(element)
               }

               override fun onFailure(error: Throwable) {
                   TODO("Not yet implemented")
               }
           })
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
         repository.addOrUpdateList(object :Listener<MutableList<GroceryListModel>>{
             override fun onSuccess(element: MutableList<GroceryListModel>) {
                 _allLists.postValue(element)
             }

             override fun onFailure(error: Throwable) {
                 TODO("Not yet implemented")
             }
         },groceryListModel)
        _editSavePressed.value =false
        navigateHome()
    }
    fun deleteList(groceryListModel: GroceryListModel){
        repository.deleteList(object :Listener<MutableList<GroceryListModel>>{
            override fun onSuccess(element: MutableList<GroceryListModel>) {
                _allLists.postValue(element)
            }

            override fun onFailure(error: Throwable) {
                TODO("Not yet implemented")
            }
        },groceryListModel)
        navigateHome()
    }
}