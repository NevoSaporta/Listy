package com.nevosap.listy.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nevosap.listy.R
import com.nevosap.listy.databinding.FragmentHomeBinding
import com.nevosap.listy.model.GroceryItemModel
import com.nevosap.listy.model.GroceryListModel
import java.time.LocalDateTime
import java.util.*

class HomeFragment:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home,container,false)

        val adapter = GroceryListAdapter(context!!)

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
                name = "bobo",
                creationDate = Date(22, 3, 1998),
                items = listOf<GroceryItemModel>(
                    GroceryItemModel(
                        name = "asdasd",
                        id = 1,
                        price = 1.1
                    )
                )

            ),   GroceryListModel(
                id = 1,
                name = "bobo",
                creationDate = Date(22, 3, 1998),
                items = listOf<GroceryItemModel>(
                    GroceryItemModel(
                        name = "asdasd",
                        id = 1,
                        price = 1.1
                    )
                )

            ),   GroceryListModel(
                id = 1,
                name = "bobo",
                creationDate = Date(22, 3, 1998),
                items = listOf<GroceryItemModel>(
                    GroceryItemModel(
                        name = "asdasd",
                        id = 1,
                        price = 1.1
                    )
                )

            ),   GroceryListModel(
                id = 1,
                name = "bobo",
                creationDate = Date(22, 3, 1998),
                items = listOf<GroceryItemModel>(
                    GroceryItemModel(
                        name = "asdasd",
                        id = 1,
                        price = 1.1
                    )
                )

            ),   GroceryListModel(
                id = 1,
                name = "bobo",
                creationDate = Date(22, 3, 1998),
                items = listOf<GroceryItemModel>(
                    GroceryItemModel(
                        name = "asdasd",
                        id = 1,
                        price = 1.1
                    )
                )

            )
        )
    }
}