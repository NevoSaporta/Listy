package com.nevosap.listy.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.nevosap.listy.R
import com.nevosap.listy.databinding.FragmentHomeBinding
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
        /*
        val builder = FirebaseDynamicLinks.getInstance().createDynamicLink()
            .setLink(Uri.parse( "https://listyapp.page.link"))
            .setDomainUriPrefix("https://listyapp.page.link")

        val link = builder.buildDynamicLink()
        val sendIntent = Intent()
        val msg = "Hey, check this out: ${link.uri}"
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, msg)
        sendIntent.type = "text/plain"
        startActivity(sendIntent)*/
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