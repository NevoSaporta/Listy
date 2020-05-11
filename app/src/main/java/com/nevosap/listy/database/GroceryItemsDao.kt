package com.nevosap.listy.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nevosap.listy.model.GroceryItemModel
@Dao
interface GroceryItemsDao {
    @Query("SELECT * FROM GroceryItemModel")
    fun getItemsInStock():MutableList<GroceryItemModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateStock(items:MutableList<GroceryItemModel>)
}