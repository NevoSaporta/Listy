package com.nevosap.listy.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nevosap.listy.R
import com.nevosap.listy.databinding.ListItemGroceryListBinding
import com.nevosap.listy.model.GroceryListModel

class GroceryListAdapter(context: Context):ListAdapter<GroceryListModel,GroceryListAdapter.ViewHolder>(GroceryListDiffCallback()) {

    private val layoutInflater: LayoutInflater = context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = layoutInflater.inflate(R.layout.list_item_grocery_list,parent,false)
        return  ViewHolder(view,parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(view: View, parent: ViewGroup):RecyclerView.ViewHolder(view){
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemGroceryListBinding.inflate(layoutInflater,parent,false)
        fun bind(groceryListModel: GroceryListModel){
            binding.groceryList =groceryListModel
            binding.executePendingBindings()
        }
    }

     class GroceryListDiffCallback:DiffUtil.ItemCallback<GroceryListModel>(){

        override fun areItemsTheSame(oldItem: GroceryListModel, newItem: GroceryListModel)= oldItem.id ==newItem.id

        override fun areContentsTheSame(oldItem: GroceryListModel, newItem: GroceryListModel)=oldItem.equals(newItem)
    }
}