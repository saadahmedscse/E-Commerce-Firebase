package com.caffeine.ecommercefirebase.services.repository

import androidx.lifecycle.MutableLiveData
import com.caffeine.ecommercefirebase.services.model.ProductDetails
import com.caffeine.ecommercefirebase.util.DataState

interface ProductInterface {

    suspend fun getCategory(categoryMutableLiveData : MutableLiveData<DataState<List<String>>>)

    suspend fun getCategorizedProducts(category : String, categorizedProductsMutableLiveData: MutableLiveData<DataState<List<ProductDetails>>>)

    suspend fun getAllProducts(allProducts: MutableLiveData<DataState<List<ProductDetails>>>)
}