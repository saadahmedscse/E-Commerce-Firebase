package com.caffeine.ecommercefirebase.util

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

object Constants {
    const val appName = "Rengvo"
    val reference : DatabaseReference = FirebaseDatabase.getInstance().reference.child(appName)
}