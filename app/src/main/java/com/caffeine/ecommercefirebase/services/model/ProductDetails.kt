package com.caffeine.ecommercefirebase.services.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductDetails(
    val id : String? = null,
    val name : String? = "",
    val price : String? = "",
    val category : String? = "",
    val sizes : ArrayList<Sizes>? = null,
    val colors : ArrayList<Colors>? = null,
    val images : ArrayList<Images>? = null,
    val totalReviews : String? = "",
    val ratings : String? = "",
    val desc : String? = "",
    val totalPurchase : String? = ""
) : Parcelable

@Parcelize
data class Sizes(
    val size : String? = ""
) : Parcelable

@Parcelize
data class Colors(
    val color : String? = ""
) : Parcelable

@Parcelize
data class Images(
    val url : String? = ""
) : Parcelable