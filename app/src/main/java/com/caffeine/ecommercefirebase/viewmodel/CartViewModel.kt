package com.caffeine.ecommercefirebase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caffeine.ecommercefirebase.services.model.CartModel
import com.caffeine.ecommercefirebase.services.repository.CartRepo
import com.caffeine.ecommercefirebase.services.repository.ProductRepo
import com.caffeine.ecommercefirebase.util.Constants
import com.caffeine.ecommercefirebase.util.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {

    private val repository : CartRepo = CartRepo()

    private val _addCarts = Constants.getMutableDataStateListOfCarts()
    val addCarts : LiveData<DataState<List<CartModel>>>
        get() = _addCarts

    private val _getCarts = Constants.getMutableDataStateListOfCarts()
    val getCarts : LiveData<DataState<List<CartModel>>>
        get() = _getCarts

    private val _removeCart = MutableLiveData<DataState<String>>()
    val removeCart : LiveData<DataState<String>>
        get() = _removeCart

    fun addToCart(cart : CartModel){
        repository.addToCart(cart, _addCarts)
    }

    fun getCartItems(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCartItems(_getCarts)
        }
    }

    fun removeCart(id : String){
        repository.removeCartItem(id, _removeCart)
    }
}