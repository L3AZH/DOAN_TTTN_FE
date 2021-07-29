package com.thuctaptotnghiep.doantttn.api.response

import com.google.gson.annotations.SerializedName

data class DataUpdateShopResponse(
    @SerializedName("message")
    var message:String
)

data class UpdateShopResponse(
    @SerializedName("code")
    var code:Int,
    @SerializedName("data")
    var data:DataDeleteProductResponse,
    @SerializedName("flag")
    var flag:Boolean
)