package com.nevosap.listy.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class GroceryItemModel (
    @PrimaryKey(autoGenerate = true)
    val id:Int ,
    val name:String,
    val price:Double
):Parcelable
