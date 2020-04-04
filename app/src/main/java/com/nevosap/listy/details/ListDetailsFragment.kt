package com.nevosap.listy.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.nevosap.listy.R
import com.nevosap.listy.databinding.FragmentDetailsBinding
import com.nevosap.listy.model.GroceryListModel

class ListDetailsFragment:Fragment() {
    private lateinit var groceryList :GroceryListModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val binding: FragmentDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_details,container,false)
        return binding.root
    }
}