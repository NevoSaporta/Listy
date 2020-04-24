package com.nevosap.listy

import android.app.Application
import com.nevosap.listy.database.DatabaseModule

class MyApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        DatabaseModule.initialize(this)
    }
}