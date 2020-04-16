package com.nevosap.listy.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nevosap.listy.R
import com.nevosap.listy.databinding.FragmentHomeBinding
import com.nevosap.listy.model.GroceryItemModel
import com.nevosap.listy.model.GroceryItemOrderModel
import com.nevosap.listy.model.GroceryListModel
import com.nevosap.listy.model.GroceryViewModel
import java.util.*

class HomeFragment:Fragment() {
    //Shared vm for all the fragments in the activity
    private val model: GroceryViewModel by activityViewModels<GroceryViewModel>()
    companion object{
        const val GROCERYLISTMODEL ="groceryListModel"
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home,container,false)
        binding.groceryViewModel = model
        initRecyclerView(binding)
        model.navigateNew.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it){
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAddEditFragment(null))
                model.navigateNewListEnded()
            }
        })
        model.navigateDetails.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToListDetailsFragment(
                        it
                    ))
            }
        })
        return binding.root
    }

    private fun initRecyclerView(binding: FragmentHomeBinding) {
        //Navigating to Details Fragment when item is pressed
        val adapter = GroceryListAdapter(model, context!!)
        binding.homeRcv.layoutManager = LinearLayoutManager(context)
        binding.homeRcv.adapter = adapter
        binding.lifecycleOwner =this
        adapter.submitList(model.allLists.value)
    }

    /*private fun getTempList(): MutableList<GroceryListModel> {
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
    }*/

}