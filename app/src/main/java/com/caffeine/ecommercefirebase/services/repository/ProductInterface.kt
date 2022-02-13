package com.caffeine.ecommercefirebase.services.repository

import androidx.lifecycle.MutableLiveData
import com.caffeine.ecommercefirebase.util.Task

interface ProductInterface {

    suspend fun getCategory(categoryMutableLiveData : MutableLiveData<Task<List<String>>>)
}