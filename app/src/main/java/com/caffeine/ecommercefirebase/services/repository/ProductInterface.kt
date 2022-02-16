package com.caffeine.ecommercefirebase.services.repository

import androidx.lifecycle.MutableLiveData
import com.caffeine.ecommercefirebase.services.model.CartModel
import com.caffeine.ecommercefirebase.services.model.ProductDetails
import com.caffeine.ecommercefirebase.util.DataState

interface ProductInterface {

    suspend fun getCategory(categoryMutableLiveData : MutableLiveData<DataState<List<String>>>)

    suspend fun getMenProducts(category : String, productsMutableLiveData: MutableLiveData<DataState<List<ProductDetails>>>)

    suspend fun getWomenProducts(category : String, productsMutableLiveData: MutableLiveData<DataState<List<ProductDetails>>>)

    suspend fun getChildProducts(category : String, productsMutableLiveData: MutableLiveData<DataState<List<ProductDetails>>>)

    suspend fun getPopularProducts(productsMutableLiveData: MutableLiveData<DataState<List<ProductDetails>>>)

    suspend fun getCategorizedProducts(category : String, productsMutableLiveData: MutableLiveData<DataState<List<ProductDetails>>>)

    suspend fun getAllProducts(allProducts: MutableLiveData<DataState<List<ProductDetails>>>)
}