package com.nevosap.listy.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nevosap.listy.model.GroceryItemModel

@Database(entities = [GroceryItemModel::class,GroceryItemModel::class],version = 1)
abstract class AppDatabase :RoomDatabase() {

    abstract fun listDao():GroceryListsDao?
    abstract fun itemDao():GroceryItemsDao?

    companion object{
        private const val DATABASE_NAME ="grocery"

        private var INSTANCE :AppDatabase? =null

        fun getInstance(context: Context):AppDatabase?{
            if(INSTANCE==null){
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE
        }

        fun destroyInstance(){
            INSTANCE =null
        }
    }
}