package com.nevosap.listy.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity
data class GroceryListModel (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name :String ="",
    val creationDate: Date,
    val orders: MutableList<GroceryItemOrderModel>,
    val users: MutableList<String> = mutableListOf()
):Parcelable
{
    @Exclude
    fun toMap():Map<String,Any>{
        return mapOf(
            "id" to id,
            "name" to name,
            "creationDate" to creationDate,
            "orders" to orders,
            "users" to users
        )
    }
}