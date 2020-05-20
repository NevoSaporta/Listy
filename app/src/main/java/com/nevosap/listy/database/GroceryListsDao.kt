package com.nevosap.listy.database

import androidx.room.*
import com.google.firebase.auth.FirebaseUser
import com.nevosap.listy.model.GroceryListModel

@Dao
interface GroceryListsDao {
    @Query("SELECT * FROM GroceryListModel")
    fun getAllLists():MutableList<GroceryListModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addOrUpdateList(groceryListModel: GroceryListModel):Long

    @Delete
    fun deleteList(groceryListModel: GroceryListModel)
}