package com.nevosap.listy.details

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nevosap.listy.ListDialogListener
import com.nevosap.listy.R
import com.nevosap.listy.databinding.FragmentDetailsBinding
import com.nevosap.listy.home.HomeFragment
import com.nevosap.listy.model.GroceryListModel
import com.nevosap.listy.model.GroceryViewModel

class ListDetailsFragment:Fragment() {
    private val model: GroceryViewModel by activityViewModels()
     private lateinit var groceryListModel :GroceryListModel
    //safe argument's name

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val binding: FragmentDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_details,container,false)
        //getting the groceryListModel from safe args
        groceryListModel = arguments?.getParcelable(HomeFragment.GROCERYLISTMODEL)!!
        model.navigateEdit.observe(viewLifecycleOwner, Observer {
            //Navigating to edit fragment
            if(it){
                findNavController().navigate(ListDetailsFragmentDirections.actionListDetailsFragmentToAddEditFragment(groceryListModel))
                model.navigateEditEnded()
            }
        })
        model.navigateHome.observe(viewLifecycleOwner , Observer {
            if(it){
                findNavController().navigate(ListDetailsFragmentDirections.actionListDetailsFragmentToHomeFragment2(null))
                model.navigateHomeEnded()
            }
        })
        binding.groceryList =groceryListModel
        setHasOptionsMenu(true)
        initRecyclerView(binding)
        return binding.root

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_details_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.edit_menu_item  ->{
                model.editListPressed()
                true
            }
            R.id.delete_menu_item ->{
                val dialog = DeleteListDialogFragment(object :
                    ListDialogListener {
                    override fun onPositiveClicked() {
                        model.deleteList(groceryListModel)
                    }

                    override fun onNegativeClicked() {
                    }
                })
                dialog.show(childFragmentManager,ListDetailsFragment::class.java.name)
                true
            }
            R.id.share_menu_item ->{
                val key = groceryListModel.id.toString()+groceryListModel.users[0]
                val context = requireActivity()
                model.shareList(key,context)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initRecyclerView(binding: FragmentDetailsBinding) {
        val adapter = GroceryItemAdapter(requireContext())
        binding.detailsRcv.layoutManager = LinearLayoutManager(context)
        binding.detailsRcv.adapter = adapter
        adapter.submitList(groceryListModel.orders)
        adapter.notifyDataSetChanged()
    }
}