package com.thuctaptotnghiep.doantttn.api.response

import com.google.gson.annotations.SerializedName

data class DataDeleteDetailShopProductResponse(
    @SerializedName("message")
    var message:String
)

data class DeleteDetailShopProductResponse(
    @SerializedName("code")
    var code:Int,
    @SerializedName("data")
    var data:DataDeleteDetailShopProductResponse,
    @SerializedName("flag")
    var flag:Boolean
)