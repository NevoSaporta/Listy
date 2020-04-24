package com.nevosap.listy.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nevosap.listy.model.GroceryItemModel

@Database(entities = [GroceryItemModel::class,GroceryItemModel::class],version = 1)
abstract class AppDatabase :RoomDatabase() {

    abstract fun listDao():GroceryListsDao
    abstract fun itemDao():GroceryItemsDao

}