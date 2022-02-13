package com.caffeine.ecommercefirebase.services.repository

import androidx.lifecycle.MutableLiveData
import com.caffeine.ecommercefirebase.util.Task

interface AuthInterface {

    fun logInUser(email : String, password : String, _userMutableLiveData : MutableLiveData<Task<String>>)

    fun createNewUser(email : String, password : String, _userMutableLiveData : MutableLiveData<Task<String>>)

    fun updateDataToFirebase(uid : String, email : String, password : String, _userMutableLiveData : MutableLiveData<Task<String>>)
}