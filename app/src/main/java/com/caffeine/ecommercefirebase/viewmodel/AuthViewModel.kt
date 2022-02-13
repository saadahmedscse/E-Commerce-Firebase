package com.caffeine.ecommercefirebase.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.caffeine.ecommercefirebase.services.repository.AuthRepo
import com.caffeine.ecommercefirebase.util.DataState

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val authRepo : AuthRepo = AuthRepo()
    private val _userMutableLiveData = MutableLiveData<DataState<String>>()

    fun createNewUser(email : String, password : String) {
        authRepo.createNewUser(email, password, _userMutableLiveData)
    }

    fun loginUser(email: String, password: String){
        authRepo.logInUser(email, password, _userMutableLiveData)
    }

    val userMutableLiveData : LiveData<DataState<String>>
        get() = _userMutableLiveData
}