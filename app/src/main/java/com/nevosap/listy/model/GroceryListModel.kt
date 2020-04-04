package com.nevosap.listy.model

import java.util.*

data class GroceryListModel (
    val id:Int,
    val name :String ="",
    val creationDate: Date,
    val items: List<GroceryItemModel>
)