package com.caffeine.ecommercefirebase.services.model

data class CartModel(
    val id : String? = null,
    val name : String? = "",
    val price : String? = "",
    val category : String? = "",
    val quantity : String? = "",
    val color : String? = "",
    val size : String? = ""
)