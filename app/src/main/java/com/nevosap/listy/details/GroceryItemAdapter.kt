package com.nevosap.listy.details

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nevosap.listy.R
import com.nevosap.listy.databinding.ListItemGroceryItemBinding
import com.nevosap.listy.model.GroceryItemModel
import com.nevosap.listy.model.GroceryItemOrderModel

class GroceryItemAdapter(context: Context):ListAdapter<GroceryItemOrderModel,GroceryItemAdapter.ViewHolder>(GroceryItemsDiffCallback()){

    private val layoutInflater: LayoutInflater = context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding:ListItemGroceryItemBinding = DataBindingUtil.inflate(layoutInflater,R.layout.list_item_grocery_item,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    inner class ViewHolder(private val binding: ListItemGroceryItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(groceryItemOrderModel: GroceryItemOrderModel){
            binding.groceryListOrder =groceryItemOrderModel
            binding.executePendingBindings()
        }
    }
    class GroceryItemsDiffCallback: DiffUtil.ItemCallback<GroceryItemOrderModel>(){

        override fun areItemsTheSame(oldItem: GroceryItemOrderModel, newItem: GroceryItemOrderModel)= oldItem.id ==newItem.id

        override fun areContentsTheSame(oldItem: GroceryItemOrderModel, newItem: GroceryItemOrderModel)=
            oldItem == newItem
    }
}