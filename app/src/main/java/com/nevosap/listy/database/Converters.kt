package com.nevosap.listy.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nevosap.listy.model.GroceryItemOrderModel
import java.util.*

class Converters {
    @TypeConverter
    fun fromDateToLong(date: Date):Long{
        return date.time
    }
    @TypeConverter
    fun fromLongToDate(timeMills: Long):Date{
        return Date(timeMills)
    }
    @TypeConverter
    fun fromOrdersToString(orders:MutableList<GroceryItemOrderModel>) :String{
        return Gson().toJson(orders)
    }
    @TypeConverter
    fun fromStringToOrders(json:String):MutableList<GroceryItemOrderModel>{
        return Gson().fromJson(json, object: TypeToken<MutableList<GroceryItemOrderModel>>(){}.type)
    }
    @TypeConverter
    fun fromUsersToString(users:MutableList<String>):String{
        return Gson().toJson(users)
    }
    @TypeConverter
    fun fromStringToUsers(json: String):MutableList<String>{
        return Gson().fromJson(json,object :TypeToken<MutableList<String>>(){}.type)
    }
}