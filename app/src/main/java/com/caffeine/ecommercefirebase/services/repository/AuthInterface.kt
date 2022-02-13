package com.caffeine.ecommercefirebase.services.repository

import androidx.lifecycle.MutableLiveData
import com.caffeine.ecommercefirebase.util.DataState

interface AuthInterface {

    fun logInUser(email : String, password : String, _userMutableLiveData : MutableLiveData<DataState<String>>)

    fun createNewUser(email : String, password : String, _userMutableLiveData : MutableLiveData<DataState<String>>)

    fun updateDataToFirebase(uid : String, email : String, password : String, _userMutableLiveData : MutableLiveData<DataState<String>>)
}