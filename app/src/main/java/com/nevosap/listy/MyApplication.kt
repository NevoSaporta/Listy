package com.nevosap.listy

import android.app.Application
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.nevosap.listy.database.DatabaseModule
import java.util.*

class MyApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        //init db
        DatabaseModule.initialize(this)
        //set auth set
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)

    }
}