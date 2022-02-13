package com.caffeine.ecommercefirebase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caffeine.ecommercefirebase.services.model.CartModel
import com.caffeine.ecommercefirebase.services.model.ProductDetails
import com.caffeine.ecommercefirebase.services.repository.ProductRepo
import com.caffeine.ecommercefirebase.util.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    private val repository : ProductRepo = ProductRepo()

    private val _categoryMutableLiveData = MutableLiveData<DataState<List<String>>>()
    val categoryLiveData : LiveData<DataState<List<String>>>
        get() = _categoryMutableLiveData

    private val _categorizedProductsMutableLiveData = MutableLiveData<DataState<List<ProductDetails>>>()
    val categorizedProductsMutableLiveData : LiveData<DataState<List<ProductDetails>>>
        get() = _categorizedProductsMutableLiveData

    private val _allProducts = MutableLiveData<DataState<List<ProductDetails>>>()
    val allProducts : LiveData<DataState<List<ProductDetails>>>
        get() = _allProducts

    private val _carts = MutableLiveData<DataState<List<CartModel>>>()
    val carts : LiveData<DataState<List<CartModel>>>
        get() = _carts

    fun getCategories(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCategory(_categoryMutableLiveData)
        }
    }

    fun getCategorizedProducts(category : String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCategorizedProducts(category, _categorizedProductsMutableLiveData)
        }
    }

    fun getAllProducts(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllProducts(_allProducts)
        }
    }

    fun addToCart(cart : CartModel){
        repository.addToCart(cart, _carts)
    }
}