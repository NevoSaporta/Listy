package com.nevosap.listy.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Debug
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.nevosap.listy.ListDialogListener
import com.nevosap.listy.MainActivity
import com.nevosap.listy.R
import com.nevosap.listy.databinding.FragmentDetailsBindingImpl
import com.nevosap.listy.databinding.FragmentHomeBinding
import com.nevosap.listy.details.DeleteListDialogFragment
import com.nevosap.listy.details.ListDetailsFragment
import com.nevosap.listy.details.ListDetailsFragmentArgs
import com.nevosap.listy.model.GroceryViewModel


class HomeFragment:Fragment() {
    //Shared vm for all the fragments in the activity
    private val model: GroceryViewModel by activityViewModels()
    private lateinit var  adapter:GroceryListAdapter
    companion object{
        const val GROCERYLISTMODEL ="groceryListModel"
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args :HomeFragmentArgs by navArgs()
        args.linkKey?.let {
            val dialog = AddSharedListDialogFragment(object :
                ListDialogListener {
                override fun onPositiveClicked() {
                    model.addSharedList(it)
                }

                override fun onNegativeClicked() {
                }
            })
            dialog.show(childFragmentManager, AddSharedListDialogFragment::class.java.name)
        }
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home,container,false)
        binding.groceryViewModel = model
        adapter  = GroceryListAdapter(model, requireContext())
        initRecyclerView(binding,adapter)
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
                model.navigateDetailsEnded()
            }
        })
        model.allLists.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            adapter.submitList(it)
        })
        return binding.root
    }

    private fun initRecyclerView(binding: FragmentHomeBinding,adapter: GroceryListAdapter) {
        //Navigating to Details Fragment when item is pressed
        binding.homeRcv.layoutManager = LinearLayoutManager(context)
        binding.homeRcv.adapter = adapter
        binding.lifecycleOwner =this
        adapter.submitList(model.allLists.value)
    }
}