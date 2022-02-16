package com.caffeine.ecommercefirebase.services.repository

import androidx.lifecycle.MutableLiveData
import com.caffeine.ecommercefirebase.services.model.CartModel
import com.caffeine.ecommercefirebase.util.DataState

interface CartInterface {

    fun addToCart(cart : CartModel, carts : MutableLiveData<DataState<List<CartModel>>>)

    suspend fun getCartItems(carts: MutableLiveData<DataState<List<CartModel>>>)

    fun removeCartItem(id : String, carts : MutableLiveData<DataState<String>>)
}