package com.nevosap.listy.networking

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nevosap.listy.model.GroceryItemModel

object FirebaseModule {
    const val ITEMS_OBJECT_NAME ="items"
    const val LISTS_OBJECT_NAME ="lists"
    const val ITEMS_NAME_PROPERTY ="name"
    const val ITEMS_PRICE_PROPERTY ="price"

    private val database = Firebase.database.reference
    lateinit var user:FirebaseUser
    val itemsRef = database.child(ITEMS_OBJECT_NAME)
    val listsRef = database.child(LISTS_OBJECT_NAME)

    fun checkInit ()= this::user.isInitialized

    fun initUser(firebaseUser: FirebaseUser){
        user = firebaseUser

    }
}