package com.caffeine.ecommercefirebase.util

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

object Constants {
    val reference : DatabaseReference = FirebaseDatabase.getInstance().reference.child("Rengvo")
}