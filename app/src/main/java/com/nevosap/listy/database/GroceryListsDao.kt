package com.nevosap.listy.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nevosap.listy.model.GroceryListModel

@Dao
interface GroceryListsDao {
    @Query("SELECT * FROM GroceryListModel")
    fun getAllLists():MutableList<GroceryListModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addOrUpdateList(groceryListModel: GroceryListModel)

}