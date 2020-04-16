package com.nevosap.listy.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nevosap.listy.R
import com.nevosap.listy.databinding.ListItemGroceryListBinding
import com.nevosap.listy.model.GroceryListModel
import com.nevosap.listy.model.GroceryViewModel

class GroceryListAdapter(private val model: GroceryViewModel, context: Context):ListAdapter<GroceryListModel,GroceryListAdapter.ViewHolder>(GroceryListDiffCallback()) {

    private val layoutInflater: LayoutInflater = context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding:ListItemGroceryListBinding = DataBindingUtil.inflate(layoutInflater,R.layout.list_item_grocery_list,parent,false)
        return  ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ListItemGroceryListBinding ):RecyclerView.ViewHolder(binding.root){
        fun bind(groceryListModel: GroceryListModel){
            //setting the binding variables
            binding.groceryList =groceryListModel
            binding.groceryViewModel =model
            binding.executePendingBindings()
        }
    }

     class GroceryListDiffCallback:DiffUtil.ItemCallback<GroceryListModel>(){

        override fun areItemsTheSame(oldItem: GroceryListModel, newItem: GroceryListModel)= oldItem.id ==newItem.id

        override fun areContentsTheSame(oldItem: GroceryListModel, newItem: GroceryListModel)=
            oldItem == newItem
    }
}