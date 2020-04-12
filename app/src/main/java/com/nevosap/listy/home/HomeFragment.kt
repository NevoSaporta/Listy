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
    companion object{
        const val GROCERYLISTMODEL ="groceryListModel"
    }
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

    private fun checkForListUpdates():MutableList<GroceryListModel> {
        val groceryListModel :GroceryListModel?= arguments?.getParcelable(GROCERYLISTMODEL)
        val tmp = getTempList()
        groceryListModel?.let {list->
            if(!tmp.none { it.id == list.id }){
                val oldList = tmp.first { it.id==list.id }
                tmp.remove(oldList)
            }
            tmp.add(groceryListModel)
        }
        return tmp
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
        adapter.submitList(checkForListUpdates())
        adapter.notifyDataSetChanged()
    }

    private fun getTempList(): MutableList<GroceryListModel> {
        return mutableListOf(
            GroceryListModel(
                id = 1,
                name = "List1",
                creationDate = Date(System.currentTimeMillis()),
                orders = mutableListOf(
                    GroceryItemOrderModel(1,
                        GroceryItemModel(
                            name = "Milk",
                            id = 1,
                            price = 1.1
                        ),2
                    ),
                    GroceryItemOrderModel(2,
                        GroceryItemModel(
                            name = "Meat",
                            id = 2,
                            price = 1.1
                        ),36
                    ) /*,  GroceryItemOrderModel(1,
                        GroceryItemModel(
                            name = "Water",
                            id = 3,
                            price = 1.1
                        ),1
                    )*/
                )
            )
        )
    }
}