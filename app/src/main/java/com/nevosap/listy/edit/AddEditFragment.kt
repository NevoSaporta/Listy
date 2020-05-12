package com.nevosap.listy.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Entity
import com.nevosap.listy.R
import com.nevosap.listy.databinding.FragmentEditBinding
import com.nevosap.listy.home.HomeFragment
import com.nevosap.listy.model.GroceryItemOrderModel
import com.nevosap.listy.model.GroceryListModel
import com.nevosap.listy.model.GroceryViewModel
import java.util.*

class AddEditFragment:Fragment() {
    private val model: GroceryViewModel by activityViewModels()
    private  var groceryListModel: GroceryListModel? = null
    private var ordersData:MutableList<GroceryItemOrderModel> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentEditBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_edit,container,false)
        binding.groceryViewModel = model
        //parsing data from safe args
        groceryListModel = arguments?.getParcelable(HomeFragment.GROCERYLISTMODEL)
        groceryListModel?.let {
            binding.editListName.setText( it.name)
            ordersData.addAll(it.orders)
        }
        val adapter = EditListAdapter(requireContext(), ordersData,parentFragmentManager)
        initRecyclerView(adapter,binding)
        model.itemsInStock.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            adapter.submitList(it)
        })
        model.navigateHome.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it){
                findNavController().navigate(AddEditFragmentDirections.actionAddEditFragmentToHomeFragment())
                model.navigateHomeEnded()
            }
        })
        model.editSavePressed.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it){
                updateList(binding,adapter)
                model.addOrUpdateList(groceryListModel!!)
            }
        })
        return binding.root
    }

    private fun updateList(binding: FragmentEditBinding, adapter: EditListAdapter) {
        if (binding.editListName.text.isNotEmpty()) {
            //new list
            groceryListModel = if (null == groceryListModel) {
                GroceryListModel(
                    id=0, name =binding.editListName.text.toString(),
                    creationDate = Date(System.currentTimeMillis()),orders =  adapter.getOrders()
                )
            } else {
                GroceryListModel(
                    groceryListModel!!.id, binding.editListName.text.toString(),
                    groceryListModel!!.creationDate, adapter.getOrders()
                )
            }
        } else {
            binding.editListName.error = getString(R.string.edit_list_name_error)
            model.errorSaving()
        }
    }

    private fun initRecyclerView(adapter:EditListAdapter,binding: FragmentEditBinding) {
        binding.editRcv.layoutManager = LinearLayoutManager(context)
        binding.editRcv.adapter = adapter
        adapter.submitList(model.itemsInStock.value)
        adapter.notifyDataSetChanged()
    }
}