package com.caffeine.ecommercefirebase.services.repository

import androidx.lifecycle.MutableLiveData
import com.caffeine.ecommercefirebase.services.model.CartModel
import com.caffeine.ecommercefirebase.services.model.ProductDetails
import com.caffeine.ecommercefirebase.util.Constants
import com.caffeine.ecommercefirebase.util.DataState
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class CartRepo : CartInterface {

    override fun addToCart(cart: CartModel, carts: MutableLiveData<DataState<List<CartModel>>>) {
        carts.postValue(DataState.Loading())
        Constants.reference
            .child("Carts")
            .child(Constants.UID!!)
            .child(cart.id!!)
            .setValue(cart)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful){
                    carts.postValue(DataState.Success())
                }
                else{
                    carts.postValue(DataState.Failed(task.exception?.message))
                }
            }
    }

    override suspend fun getCartItems(carts: MutableLiveData<DataState<List<CartModel>>>
    ) {
        carts.postValue(DataState.Loading())
        val tempList = ArrayList<CartModel>()
        Constants.reference.child("Carts").child(Constants.UID!!)
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    tempList.clear()

                    for (ds in snapshot.children){
                        ds.getValue(CartModel::class.java)?.let { tempList.add(it) }
                    }

                    carts.postValue(DataState.Success(tempList))
                }

                override fun onCancelled(error: DatabaseError) {
                    carts.postValue(DataState.Failed(error.message))
                }

            })
    }

    override fun removeCartItem(id: String, carts : MutableLiveData<DataState<String>>) {
        carts.postValue(DataState.Loading())
        Constants.reference.child("Carts").child(Constants.UID!!).child(id).removeValue()
            .addOnCompleteListener {
                if (it.isSuccessful){
                    carts.postValue(DataState.Success("Cart has been deleted"))
                }
                else carts.postValue(DataState.Failed(it.exception?.message))
            }
    }
}