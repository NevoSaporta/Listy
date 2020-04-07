package com.nevosap.listy.home

/*
* interface for recyclerView on click event*/
import com.nevosap.listy.model.GroceryListModel

class GroceryListClickListener(val clickListener :(groceryListModel: GroceryListModel)->Unit) {
    fun onClick(groceryListModel: GroceryListModel){
        clickListener(groceryListModel)
    }
}