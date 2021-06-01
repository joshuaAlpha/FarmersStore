package com.example.farmersstore.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Product (
    var  id : String = "",
    var userId : String = "",
    var userName : String = "",
    val title : String = "",
    val  description : String = "",
    val price : Double = 0.0,
    val quantity : Int,
    val  imageUrl : String = "",
        ) : Parcelable