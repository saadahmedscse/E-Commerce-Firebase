package com.caffeine.ecommercefirebase.services.repository

import androidx.lifecycle.MutableLiveData
import com.caffeine.ecommercefirebase.services.model.ProductDetails
import com.caffeine.ecommercefirebase.util.Constants
import com.caffeine.ecommercefirebase.util.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class ProductRepo : ProductInterface {

    override suspend fun getCategory(categoryMutableLiveData: MutableLiveData<Task<List<String>>>) {
        categoryMutableLiveData.postValue(Task.Loading())

        val tempList = ArrayList<String>()
        Constants.reference.child("Categories").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children){
                    ds.getValue(String::class.java)?.let { tempList.add(it) }
                }
                categoryMutableLiveData.postValue(Task.Success(tempList))
            }

            override fun onCancelled(error: DatabaseError) {
                categoryMutableLiveData.postValue(Task.Failed(error.message))
            }

        })
    }

    override suspend fun getCategorizedProducts(
        category: String,
        categorizedProductsMutableLiveData: MutableLiveData<Task<List<ProductDetails>>>
    ) {
        categorizedProductsMutableLiveData.postValue(Task.Loading())

        val tempList = ArrayList<ProductDetails>()
        Constants.reference.child("Products").child(category).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children){
                    ds.getValue(ProductDetails::class.java)?.let { tempList.add(it) }
                }
                categorizedProductsMutableLiveData.postValue(Task.Success(tempList))
            }

            override fun onCancelled(error: DatabaseError) {
                categorizedProductsMutableLiveData.postValue(Task.Failed(error.message))
            }

        })
    }

    override suspend fun getAllProducts(allProducts: MutableLiveData<Task<List<ProductDetails>>>) {
        allProducts.postValue(Task.Loading())

        val tempList = ArrayList<ProductDetails>()
        Constants.reference.child("Products").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children){
                    for (sds in ds.children){
                        sds.getValue(ProductDetails::class.java)?.let { tempList.add(it) }
                    }
                }
                allProducts.postValue(Task.Success(tempList))
            }

            override fun onCancelled(error: DatabaseError) {
                allProducts.postValue(Task.Failed(error.message))
            }

        })
    }
}