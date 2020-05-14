package com.nevosap.listy.networking

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nevosap.listy.model.GroceryItemModel

object FirebaseModule {
    private val database = Firebase.database.reference
    const val ITEMS_OBJECT_NAME ="items"
    const val ITEMS_NAME_PROPERTY ="name"
    const val ITEMS_PRICE_PROPERTY ="price"
    val itemsRef = database.child(ITEMS_OBJECT_NAME)
}