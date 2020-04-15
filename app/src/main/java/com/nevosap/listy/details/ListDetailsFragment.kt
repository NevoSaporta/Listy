package com.nevosap.listy.details

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nevosap.listy.R
import com.nevosap.listy.databinding.FragmentDetailsBinding
import com.nevosap.listy.home.HomeFragment
import com.nevosap.listy.model.GroceryListModel

class ListDetailsFragment:Fragment() {
    private lateinit var groceryListModel :GroceryListModel
    //safe argument's name

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val binding: FragmentDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_details,container,false)
        //getting the groceryListModel from safe args
        groceryListModel = arguments?.getParcelable(HomeFragment.GROCERYLISTMODEL)!!
        binding.groceryList =groceryListModel
        setHasOptionsMenu(true)
        initRecyclerView(binding)
        return binding.root

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.details_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.edit_menu_item  ->{
                //Navigating to edit fragment
                findNavController().navigate(ListDetailsFragmentDirections.actionListDetailsFragmentToAddEditFragment(groceryListModel))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initRecyclerView(binding: FragmentDetailsBinding) {
        val adapter = GroceryItemAdapter(context!!)
        binding.detailsRcv.layoutManager = LinearLayoutManager(context)
        binding.detailsRcv.adapter = adapter
        adapter.submitList(groceryListModel.orders)
        adapter.notifyDataSetChanged()
    }
}