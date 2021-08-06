package com.thuctaptotnghiep.doantttn.api.request

import com.google.gson.annotations.SerializedName


data class UpdatePriceListRequest(
    @SerializedName("price")
    var price:Double,
    @SerializedName("image")
    var image: ByteArray
)