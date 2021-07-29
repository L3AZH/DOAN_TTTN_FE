package com.thuctaptotnghiep.doantttn.api.response

import com.google.gson.annotations.SerializedName

data class DataDeleteShopResponse(
    @SerializedName("message")
    var message:String
)

data class DeleteShopResponse(
    @SerializedName("code")
    var code:Int,
    @SerializedName("data")
    var data:DataDeleteShopResponse,
    @SerializedName("flag")
    var flag:Boolean
)