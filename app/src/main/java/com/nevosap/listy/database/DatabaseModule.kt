package com.nevosap.listy.database

import android.app.Application
import android.content.Context
import androidx.room.Room

object DatabaseModule{
    private const val DATABASE_NAME ="grocery"
    lateinit var groceryItemsDao: GroceryItemsDao
    lateinit var groceryListsDao: GroceryListsDao

    fun initialize(application: Application){
        val db = Room.databaseBuilder(
            application,AppDatabase::class.java, DATABASE_NAME).fallbackToDestructiveMigration()
            .build()
        groceryItemsDao =db.itemDao()
        groceryListsDao =db.listDao()
    }
}