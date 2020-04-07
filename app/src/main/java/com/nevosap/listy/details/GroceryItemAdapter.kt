package com.nevosap.listy.details

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nevosap.listy.R
import com.nevosap.listy.databinding.ListItemGroceryItemBinding
import com.nevosap.listy.model.GroceryItemModel

class GroceryItemAdapter(private val data: MutableMap<GroceryItemModel,Int>, context: Context):RecyclerView.Adapter<GroceryItemAdapter.ViewHolder>() {

    private val layoutInflater: LayoutInflater = context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding:ListItemGroceryItemBinding = DataBindingUtil.inflate(layoutInflater,R.layout.list_item_grocery_item,parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount()= data.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      //TODO: Change model
    }
    inner class ViewHolder(private val binding: ListItemGroceryItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(groceryItemModel: GroceryItemModel, quantity:Int){
            binding.groceryListItem =groceryItemModel
            binding.quantity =quantity
            binding.executePendingBindings()
        }
    }
}