package com.caffeine.ecommercefirebase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caffeine.ecommercefirebase.services.repository.ProductRepo
import com.caffeine.ecommercefirebase.util.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    private val repository : ProductRepo = ProductRepo()
    private val _categoryMutableLiveData = MutableLiveData<Task<List<String>>>()

    fun getCategories(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCategory(_categoryMutableLiveData)
        }
    }

    val categoryLiveData : LiveData<Task<List<String>>>
        get() = _categoryMutableLiveData
}