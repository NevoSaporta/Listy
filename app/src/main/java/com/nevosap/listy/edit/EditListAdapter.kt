package com.nevosap.listy.edit

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nevosap.listy.R
import com.nevosap.listy.databinding.ListItemGroceryEditBinding
import com.nevosap.listy.model.GroceryItemModel

class EditListAdapter(context: Context):ListAdapter<GroceryItemModel,EditListAdapter.ViewHolder>(GroceryEditDiffCallback()) {

    private val layoutInflater: LayoutInflater = context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditListAdapter.ViewHolder {
        val binding:ListItemGroceryEditBinding = DataBindingUtil.inflate(layoutInflater,
            R.layout.list_item_grocery_edit,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EditListAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ListItemGroceryEditBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(groceryItemModel: GroceryItemModel){
           binding.groceryItemModel = groceryItemModel
           binding.executePendingBindings()
        }
    }

    class GroceryEditDiffCallback: DiffUtil.ItemCallback<GroceryItemModel>(){

        override fun areItemsTheSame(oldItem: GroceryItemModel, newItem: GroceryItemModel)= oldItem.id ==newItem.id

        override fun areContentsTheSame(oldItem: GroceryItemModel, newItem: GroceryItemModel)=
            oldItem == newItem
    }

}