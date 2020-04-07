package com.nevosap.listy


import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.nevosap.listy.R
import com.nevosap.listy.model.GroceryItemOrderModel
import com.nevosap.listy.model.GroceryListModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("listCreatedDateFormatted")
fun TextView.listCreatedDateFormatted(groceryListModel: GroceryListModel){
    val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
    text = resources.getString(R.string.list_item_date_format,formatter.format(groceryListModel.creationDate).toString())
}
@BindingAdapter("quantityFormatted")
fun TextView.quantityFormatted(orderModel: GroceryItemOrderModel){
    text = resources.getString(R.string.list_item_quantity_format,orderModel.quantity.toString())
}