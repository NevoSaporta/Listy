package com.nevosap.listy.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GroceryItemOrderModel (
    val id:Int,
    val item: GroceryItemModel,
    val quantity :Int
):Parcelable