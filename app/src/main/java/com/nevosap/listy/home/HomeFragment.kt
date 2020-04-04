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
                name = "List1",
                creationDate = Date(System.currentTimeMillis()),
                items = mutableListOf(
                    GroceryItemModel(
                        name = "asdasd",
                        id = 1,
                        price = 1.1
                    )
                )

            ),   GroceryListModel(
                id = 2,
                name = "List2",
                creationDate = Date(System.currentTimeMillis()),
                items = mutableListOf(
                    GroceryItemModel(
                        name = "asdasd",
                        id = 1,
                        price = 1.1
                    )
                )

            ),   GroceryListModel(
                id = 3,
                name = "List3",
                creationDate = Date(System.currentTimeMillis()),
                items = mutableListOf(
                    GroceryItemModel(
                        name = "asdasd",
                        id = 1,
                        price = 1.1
                    )
                )

            ),   GroceryListModel(
                id = 4,
                name = "List4",
                creationDate =Date(System.currentTimeMillis()),
                items = mutableListOf(
                    GroceryItemModel(
                        name = "asdasd",
                        id = 1,
                        price = 1.1
                    )
                )

            ),   GroceryListModel(
                id = 5,
                name = "List5",
                creationDate = Date(System.currentTimeMillis()),
                items = mutableListOf(
                    GroceryItemModel(
                        name = "asdasd",
                        id = 1,
                        price = 1.1
                    )
                )

            ),   GroceryListModel(
                id = 6,
                name = "List6",
                creationDate = Date(System.currentTimeMillis()),
                items = mutableListOf(
                    GroceryItemModel(
                        name = "asdasd",
                        id = 1,
                        price = 1.1
                    )
                )

            ),   GroceryListModel(
                id = 7,
                name = "List7",
                creationDate = Date(System.currentTimeMillis()),
                items = mutableListOf(
                    GroceryItemModel(
                        name = "asdasd",
                        id = 1,
                        price = 1.1
                    )
                )

            ),   GroceryListModel(
                id = 8,
                name = "List8",
                creationDate = Date(System.currentTimeMillis()),
                items = mutableListOf(
                    GroceryItemModel(
                        name = "asdasd",
                        id = 1,
                        price = 1.1
                    )
                )

            ),   GroceryListModel(
                id = 8,
                name = "List8",
                creationDate = Date(System.currentTimeMillis()),
                items = mutableListOf(
                    GroceryItemModel(
                        name = "asdasd",
                        id = 1,
                        price = 1.1
                    )
                )

            ),   GroceryListModel(
                id = 9,
                name = "List9",
                creationDate = Date(System.currentTimeMillis()),
                items = mutableListOf(
                    GroceryItemModel(
                        name = "asdasd",
                        id = 1,
                        price = 1.1
                    )
                )

            ),   GroceryListModel(
                id = 10,
                name = "List10",
                creationDate = Date(System.currentTimeMillis()),
                items = mutableListOf(
                    GroceryItemModel(
                        name = "asdasd",
                        id = 1,
                        price = 1.1
                    )
                )

            ),   GroceryListModel(
                id = 11,
                name = "List11",
                creationDate = Date(System.currentTimeMillis()),
                items = mutableListOf(
                    GroceryItemModel(
                        name = "asdasd",
                        id = 1,
                        price = 1.1
                    )
                )

            ),   GroceryListModel(
                id = 12,
                name = "List12",
                creationDate = Date(System.currentTimeMillis()),
                items =mutableListOf(
                    GroceryItemModel(
                        name = "asdasd",
                        id = 1,
                        price = 1.1
                    )
                )

            ),   GroceryListModel(
                id = 13,
                name = "List13",
                creationDate = Date(System.currentTimeMillis()),
                items = mutableListOf(
                    GroceryItemModel(
                        name = "asdasd",
                        id = 1,
                        price = 1.1
                    )
                )

            ),   GroceryListModel(
                id = 14,
                name = "List14",
                creationDate = Date(System.currentTimeMillis()),
                items = mutableListOf(
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