package com.nevosap.listy.home


import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.nevosap.listy.R
import com.nevosap.listy.model.GroceryListModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("listCreatedDateFormatted")
fun TextView.listCreatedDateFormatted(groceryListModel: GroceryListModel){
    val formatter = DateFormat.getDateInstance(DateFormat.LONG, Locale.ENGLISH)
    text = resources.getString(R.string.list_item_date_format,formatter.format(groceryListModel).toString())
}