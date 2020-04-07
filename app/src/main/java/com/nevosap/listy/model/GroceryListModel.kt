package com.nevosap.listy.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class GroceryListModel (
    val id:Int,
    val name :String ="",
    val creationDate: Date,
    val items: MutableList<GroceryItemModel>
):Parcelable