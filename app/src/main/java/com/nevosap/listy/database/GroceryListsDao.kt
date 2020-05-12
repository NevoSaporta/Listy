package com.nevosap.listy.database

import androidx.room.*
import com.nevosap.listy.model.GroceryListModel

@Dao
interface GroceryListsDao {
    @Query("SELECT * FROM GroceryListModel")
    fun getAllLists():MutableList<GroceryListModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addOrUpdateList(groceryListModel: GroceryListModel)

    @Delete
    fun deleteList(groceryListModel: GroceryListModel)
}