package com.nevosap.listy.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GroceryItemModel (
    val id:Int ,
    val name:String,
    val price:Double
):Parcelable
