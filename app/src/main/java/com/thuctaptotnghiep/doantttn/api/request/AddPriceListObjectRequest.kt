package com.thuctaptotnghiep.doantttn.api.request

import com.google.gson.annotations.SerializedName

data class AddPriceListObjectRequest(
    @SerializedName("idShop")
    var idShop:String,
    @SerializedName("idProduct")
    var idProduct:String,
    @SerializedName("price")
    var price:Double,
    @SerializedName("image")
    var image:ByteArray
)