package com.caffeine.ecommercefirebase.services.repository

import androidx.lifecycle.MutableLiveData
import com.caffeine.ecommercefirebase.services.model.UserDetails
import com.caffeine.ecommercefirebase.util.Constants
import com.caffeine.ecommercefirebase.util.DataState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class AuthRepo : AuthInterface {
    private var auth : FirebaseAuth = FirebaseAuth.getInstance()
    private var ref : DatabaseReference = Constants.reference

    override fun logInUser(email: String, password: String, _userMutableLiveData : MutableLiveData<DataState<String>>) {
        _userMutableLiveData.postValue(DataState.Loading())
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if (it.isSuccessful){
                    _userMutableLiveData.postValue(DataState.Success())
                }
                else{
                    _userMutableLiveData.postValue(DataState.Failed(it.exception?.message))
                }
            }
    }

    override fun createNewUser(email: String, password: String, _userMutableLiveData : MutableLiveData<DataState<String>>) {
        _userMutableLiveData.postValue(DataState.Loading())
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if (it.isSuccessful){
                    updateDataToFirebase(FirebaseAuth.getInstance().uid!!, email, password, _userMutableLiveData)
                }
                else{
                    _userMutableLiveData.postValue(DataState.Failed(it.exception?.message))
                }
            }
    }

    override fun updateDataToFirebase(uid: String, email: String, password: String, _userMutableLiveData : MutableLiveData<DataState<String>>) {
        val user = UserDetails(uid, email, password)

        ref.child("Users").child(uid).setValue(user)
            .addOnCompleteListener{
                if (it.isSuccessful){
                    _userMutableLiveData.postValue(DataState.Success())
                }
                else{
                    _userMutableLiveData.postValue(DataState.Failed(it.exception?.message))
                }
            }
    }
}