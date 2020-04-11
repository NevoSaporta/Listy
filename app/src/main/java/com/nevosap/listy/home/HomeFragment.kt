package com.nevosap.listy.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nevosap.listy.R
import com.nevosap.listy.databinding.FragmentHomeBinding
import com.nevosap.listy.model.GroceryItemModel
import com.nevosap.listy.model.GroceryItemOrderModel
import com.nevosap.listy.model.GroceryListModel
import java.util.*

class HomeFragment:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home,container,false)
        initRecyclerView(binding)
        binding.addListBtn.setOnClickListener {
           findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAddEditFragment(null))
        }
        return binding.root
    }

    private fun initRecyclerView(binding: FragmentHomeBinding) {
        //Navigating to Details Fragment when item is pressed
        val adapter = GroceryListAdapter(GroceryListClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToListDetailsFragment(
                    it
                )
            )
        }, context!!)
        binding.homeRcv.layoutManager = LinearLayoutManager(context)
        binding.homeRcv.adapter = adapter
        val tmpList: List<GroceryListModel> = getTempList()
        adapter.submitList(tmpList)
        adapter.notifyDataSetChanged()
    }

    private fun getTempList(): List<GroceryListModel> {
        return listOf(
            GroceryListModel(
                id = 1,
                name = "List1",
                creationDate = Date(System.currentTimeMillis()),
                items = mutableListOf(
                    GroceryItemOrderModel(1,
                        GroceryItemModel(
                            name = "Cookie",
                            id = 1,
                            price = 1.1
                        ),2
                    ),
                    GroceryItemOrderModel(1,
                        GroceryItemModel(
                            name = "Koala",
                            id = 2,
                            price = 1.1
                        ),36
                    ) ,  GroceryItemOrderModel(1,
                        GroceryItemModel(
                            name = "Socks",
                            id = 3,
                            price = 1.1
                        ),1
                    )
                )
            )
        )
    }
}