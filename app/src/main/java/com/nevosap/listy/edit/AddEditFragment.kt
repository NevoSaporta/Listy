package com.nevosap.listy.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nevosap.listy.R
import com.nevosap.listy.databinding.FragmentEditBinding
import com.nevosap.listy.home.HomeFragment
import com.nevosap.listy.home.HomeFragmentDirections
import com.nevosap.listy.model.GroceryItemModel
import com.nevosap.listy.model.GroceryItemOrderModel
import com.nevosap.listy.model.GroceryListModel
import java.util.*

class AddEditFragment:Fragment() {
    private  var groceryListModel: GroceryListModel? = null
    private var ordersData:MutableList<GroceryItemOrderModel>?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentEditBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_edit,container,false)
        val adapter = EditListAdapter(context!!,ordersData)
        //parsing data from safe args
        groceryListModel = arguments?.getParcelable(HomeFragment.GROCERYLISTMODEL)
        groceryListModel?.let {
            binding.editListName.setText( it.name)
            ordersData =it.orders
        }
        initRecyclerView(adapter,binding)
        binding.cancelEditBtn.setOnClickListener{
            findNavController().navigate(AddEditFragmentDirections.actionAddEditFragmentToHomeFragment(null))
        }
        binding.saveEditBtn.setOnClickListener{
            if(binding.editListName.text.isNotEmpty()){
                //new list
                groceryListModel = if(null==groceryListModel){
                    //todo auto generate id
                    GroceryListModel(100,binding.editListName.text.toString(),
                        Date(System.currentTimeMillis()),adapter.getOrders())
                }else{
                    GroceryListModel(  groceryListModel!!.id,binding.editListName.text.toString(),
                            groceryListModel!!.creationDate,adapter.getOrders())
                }
                findNavController().navigate(AddEditFragmentDirections.actionAddEditFragmentToHomeFragment(groceryListModel))
            }else{
                binding.editListName.error = getString(R.string.edit_list_name_error)
            }
        }

        return binding.root
    }
    private fun initRecyclerView(adapter:EditListAdapter,binding: FragmentEditBinding) {

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