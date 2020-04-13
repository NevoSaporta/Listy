package com.nevosap.listy.edit

import com.nevosap.listy.databinding.ListItemGroceryEditBinding
import com.nevosap.listy.model.GroceryItemModel
import com.nevosap.listy.model.GroceryItemOrderModel

class EditListClickListener (val clickListener: (MutableList<GroceryItemOrderModel>?, ListItemGroceryEditBinding, GroceryItemModel)->Unit){

}