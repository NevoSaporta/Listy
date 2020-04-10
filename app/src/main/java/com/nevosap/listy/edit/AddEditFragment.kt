package com.nevosap.listy.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.nevosap.listy.R
import com.nevosap.listy.databinding.FragmentEditBinding
import com.nevosap.listy.model.GroceryItemModel

class AddEditFragment:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentEditBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_edit,container,false)
        initRecyclerView(binding)
        return binding.root
    }
    private fun initRecyclerView(binding: FragmentEditBinding) {
        val adapter = EditListAdapter(context!!)
        binding.editRcv.layoutManager = LinearLayoutManager(context)
        binding.editRcv.adapter = adapter
        adapter.submitList(getTmpList())
        adapter.notifyDataSetChanged()
    }
    private fun getTmpList ()= listOf(
        GroceryItemModel(1,"milk",1.1),
        GroceryItemModel(2,"meat",2.1),
        GroceryItemModel(3,"water",3.1)
    )
}