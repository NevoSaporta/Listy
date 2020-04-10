package com.nevosap.listy.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.nevosap.listy.R
import com.nevosap.listy.databinding.FragmentDetailsBinding
import com.nevosap.listy.model.GroceryListModel

class ListDetailsFragment:Fragment() {
    private lateinit var groceryListModel :GroceryListModel
    //safe argument's name
    companion object{
        const val GROCERYLISTMODEL ="groceryListModel"
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val binding: FragmentDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_details,container,false)
        //getting the groceryListModel from safe args
        groceryListModel = arguments?.getParcelable(GROCERYLISTMODEL)!!
        binding.groceryList =groceryListModel

        initRecyclerView(binding)
        return binding.root

    }

    private fun initRecyclerView(binding: FragmentDetailsBinding) {
        val adapter = GroceryItemAdapter(context!!)
        binding.detailsRcv.layoutManager = LinearLayoutManager(context)
        binding.detailsRcv.adapter = adapter
        adapter.submitList(groceryListModel.items)
        adapter.notifyDataSetChanged()
    }
}