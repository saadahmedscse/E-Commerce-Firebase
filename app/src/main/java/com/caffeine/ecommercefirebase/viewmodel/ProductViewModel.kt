package com.caffeine.ecommercefirebase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caffeine.ecommercefirebase.services.model.ProductDetails
import com.caffeine.ecommercefirebase.services.repository.ProductRepo
import com.caffeine.ecommercefirebase.util.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    private val repository : ProductRepo = ProductRepo()

    private val _categoryMutableLiveData = MutableLiveData<Task<List<String>>>()
    val categoryLiveData : LiveData<Task<List<String>>>
        get() = _categoryMutableLiveData

    private val _categorizedProductsMutableLiveData = MutableLiveData<Task<List<ProductDetails>>>()
    val categorizedProductsMutableLiveData : LiveData<Task<List<ProductDetails>>>
        get() = _categorizedProductsMutableLiveData

    private val _allProducts = MutableLiveData<Task<List<ProductDetails>>>()
    val allProducts : LiveData<Task<List<ProductDetails>>>
        get() = _allProducts

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
}