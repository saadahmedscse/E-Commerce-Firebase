package com.caffeine.ecommercefirebase.util

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.caffeine.ecommercefirebase.services.model.CartModel
import com.caffeine.ecommercefirebase.services.model.ProductDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

object Constants {
    const val appName = "Rengvo"
    val reference : DatabaseReference = FirebaseDatabase.getInstance().reference.child(appName)
    val UID = FirebaseAuth.getInstance().uid

    fun getHorizontalLayout(context: Context) : LinearLayoutManager{
        return LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    fun getMutableDataStateListOfProductDetails() : MutableLiveData<DataState<List<ProductDetails>>>{
        return MutableLiveData<DataState<List<ProductDetails>>>()
    }

    fun getMutableDataStateListOfCarts() : MutableLiveData<DataState<List<CartModel>>>{
        return MutableLiveData<DataState<List<CartModel>>>()
    }
}