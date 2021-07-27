package com.thuctaptotnghiep.doantttn.api.response

import com.google.gson.annotations.SerializedName

data class DataUpdateProductResponse(
    @SerializedName("message")
    var message:String
)

data class UpdateProductResponse(
    @SerializedName("code")
    var code:Int,
    @SerializedName("data")
    var data:DataDeleteProductResponse,
    @SerializedName("flag")
    var flag:Boolean
)