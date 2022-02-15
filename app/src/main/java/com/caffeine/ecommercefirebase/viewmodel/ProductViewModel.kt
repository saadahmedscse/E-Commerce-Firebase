package com.caffeine.ecommercefirebase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caffeine.ecommercefirebase.services.model.CartModel
import com.caffeine.ecommercefirebase.services.model.ProductDetails
import com.caffeine.ecommercefirebase.services.repository.ProductRepo
import com.caffeine.ecommercefirebase.util.Constants
import com.caffeine.ecommercefirebase.util.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    private val repository : ProductRepo = ProductRepo()

    private val _categoryMutableLiveData = MutableLiveData<DataState<List<String>>>()
    val categoryLiveData : LiveData<DataState<List<String>>>
        get() = _categoryMutableLiveData

    private val _menProductMutableLiveData = Constants.getMutableDataStateListOfProductDetails()
    val menProductMutableLiveData : LiveData<DataState<List<ProductDetails>>>
        get() = _menProductMutableLiveData

    private val _womenProductMutableLiveData = Constants.getMutableDataStateListOfProductDetails()
    val womenProductMutableLiveData : LiveData<DataState<List<ProductDetails>>>
        get() = _womenProductMutableLiveData

    private val _childProductMutableLiveData = Constants.getMutableDataStateListOfProductDetails()
    val childProductMutableLiveData : LiveData<DataState<List<ProductDetails>>>
        get() = _childProductMutableLiveData

    private val _popularProductMutableLiveData = Constants.getMutableDataStateListOfProductDetails()
    val popularProductMutableLiveData : LiveData<DataState<List<ProductDetails>>>
        get() = _popularProductMutableLiveData

    private val _allProducts = Constants.getMutableDataStateListOfProductDetails()
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

    fun getMenProducts(category: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getMenProducts(category, _menProductMutableLiveData)
        }
    }

    fun getWomenProducts(category: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getWomenProducts(category, _womenProductMutableLiveData)
        }
    }

    fun getChildProducts(category: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getChildProducts(category, _childProductMutableLiveData)
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