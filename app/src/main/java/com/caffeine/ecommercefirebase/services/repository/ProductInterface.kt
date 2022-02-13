package com.caffeine.ecommercefirebase.services.repository

import androidx.lifecycle.MutableLiveData
import com.caffeine.ecommercefirebase.services.model.ProductDetails
import com.caffeine.ecommercefirebase.util.Task

interface ProductInterface {

    suspend fun getCategory(categoryMutableLiveData : MutableLiveData<Task<List<String>>>)

    suspend fun getCategorizedProducts(category : String, categorizedProductsMutableLiveData: MutableLiveData<Task<List<ProductDetails>>>)

    suspend fun getAllProducts(allProducts: MutableLiveData<Task<List<ProductDetails>>>)
}