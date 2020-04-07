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
import com.nevosap.listy.model.GroceryListModel
import java.util.*

class HomeFragment:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home,container,false)

        val adapter = GroceryListAdapter(GroceryListClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToListDetailsFragment(it))
        },context!!)

        binding.homeRcv.layoutManager = LinearLayoutManager(context)
        binding.homeRcv.adapter =adapter
        val tmpList : List<GroceryListModel> = getTempList()
        adapter.submitList(tmpList)
        adapter.notifyDataSetChanged()
        return binding.root
    }

    private fun getTempList(): List<GroceryListModel> {
        return listOf(
            GroceryListModel(
                id = 1,
                name = "List1",
                creationDate = Date(System.currentTimeMillis()),
                items = mutableMapOf(
                    GroceryItemModel(
                        name = "asdasd",
                        id = 1,
                        price = 1.1
                    ) to 1
                )

            )
            ,   GroceryListModel(
                id = 3,
                name = "List3",
                creationDate = Date(System.currentTimeMillis()),
                items = mutableMapOf(
                    GroceryItemModel(
                        name = "asdasd",
                        id = 1,
                        price = 1.1
                    ) to 1
                )
            ),   GroceryListModel(
                id = 3,
                name = "List3",
                creationDate = Date(System.currentTimeMillis()),
                items = mutableMapOf(
                    GroceryItemModel(
                        name = "asdasd",
                        id = 1,
                        price = 1.1
                    ) to 1
                )
            ),   GroceryListModel(
                id = 3,
                name = "List7",
                creationDate = Date(System.currentTimeMillis()),
                items = mutableMapOf(
                    GroceryItemModel(
                        name = "asdasd",
                        id = 1,
                        price = 1.1
                    ) to 1
                )
            ),   GroceryListModel(
                id = 45,
                name = "List5",
                creationDate = Date(System.currentTimeMillis()),
                items = mutableMapOf(
                    GroceryItemModel(
                        name = "asdasd",
                        id = 1,
                        price = 1.1
                    ) to 1
                )
            ),   GroceryListModel(
                id = 45,
                name = "List52",
                creationDate = Date(System.currentTimeMillis()),
                items = mutableMapOf(
                    GroceryItemModel(
                        name = "asdasd",
                        id = 1,
                        price = 1.1
                    ) to 1
                )
            ),   GroceryListModel(
                id = 45,
                name = "List5111",
                creationDate = Date(System.currentTimeMillis()),
                items = mutableMapOf(
                    GroceryItemModel(
                        name = "asdasd",
                        id = 1,
                        price = 1.1
                    ) to 1
                )
            ),   GroceryListModel(
                id = 45,
                name = "List544",
                creationDate = Date(System.currentTimeMillis()),
                items = mutableMapOf(
                    GroceryItemModel(
                        name = "asdasd",
                        id = 1,
                        price = 1.1
                    ) to 1
                )
            ),   GroceryListModel(
                id = 45,
                name = "List59",
                creationDate = Date(System.currentTimeMillis()),
                items = mutableMapOf(
                    GroceryItemModel(
                        name = "asdasd",
                        id = 1,
                        price = 1.1
                    ) to 1
                )
            ),   GroceryListModel(
                id = 45,
                name = "List85",
                creationDate = Date(System.currentTimeMillis()),
                items = mutableMapOf(
                    GroceryItemModel(
                        name = "asdasd",
                        id = 1,
                        price = 1.1
                    ) to 1
                )
            )
        )
    }
}