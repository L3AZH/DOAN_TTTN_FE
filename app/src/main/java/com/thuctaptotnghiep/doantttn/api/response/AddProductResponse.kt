package com.thuctaptotnghiep.doantttn.api.response

import com.google.gson.annotations.SerializedName

data class DataAddProductResponse(
    @SerializedName("message")
    var message:String,
    @SerializedName("newObject")
    var newObject:Category
)

data class AddProductResponse(
    @SerializedName("code")
    var code:Int,
    @SerializedName("data")
    var data:DataAddProductResponse,
    @SerializedName("flag")
    var flag:Boolean
)