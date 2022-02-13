package com.caffeine.ecommercefirebase.services.repository

import androidx.lifecycle.MutableLiveData
import com.caffeine.ecommercefirebase.services.model.CartModel
import com.caffeine.ecommercefirebase.services.model.ProductDetails
import com.caffeine.ecommercefirebase.util.Constants
import com.caffeine.ecommercefirebase.util.DataState
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ProductRepo : ProductInterface {

    override suspend fun getCategory(categoryMutableLiveData: MutableLiveData<DataState<List<String>>>) {
        categoryMutableLiveData.postValue(DataState.Loading())

        val tempList = ArrayList<String>()
        Constants.reference.child("Categories").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                tempList.clear()
                for (ds in snapshot.children){
                    ds.getValue(String::class.java)?.let { tempList.add(it) }
                }
                categoryMutableLiveData.postValue(DataState.Success(tempList))
            }

            override fun onCancelled(error: DatabaseError) {
                categoryMutableLiveData.postValue(DataState.Failed(error.message))
            }

        })
    }

    override suspend fun getCategorizedProducts(
        category: String,
        categorizedProductsMutableLiveData: MutableLiveData<DataState<List<ProductDetails>>>
    ) {
        categorizedProductsMutableLiveData.postValue(DataState.Loading())

        val tempList = ArrayList<ProductDetails>()
        Constants.reference.child("Products").child(category).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                tempList.clear()
                for (ds in snapshot.children){
                    ds.getValue(ProductDetails::class.java)?.let { tempList.add(it) }
                }
                categorizedProductsMutableLiveData.postValue(DataState.Success(tempList))
            }

            override fun onCancelled(error: DatabaseError) {
                categorizedProductsMutableLiveData.postValue(DataState.Failed(error.message))
            }

        })
    }

    override suspend fun getAllProducts(allProducts: MutableLiveData<DataState<List<ProductDetails>>>) {
        allProducts.postValue(DataState.Loading())

        val tempList = ArrayList<ProductDetails>()
        Constants.reference.child("Products").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                tempList.clear()
                for (ds in snapshot.children){
                    for (sds in ds.children){
                        sds.getValue(ProductDetails::class.java)?.let { tempList.add(it) }
                    }
                }
                allProducts.postValue(DataState.Success(tempList))
            }

            override fun onCancelled(error: DatabaseError) {
                allProducts.postValue(DataState.Failed(error.message))
            }

        })
    }

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
}