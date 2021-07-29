package com.thuctaptotnghiep.doantttn.api.response

import com.google.gson.annotations.SerializedName

data class DataAddShopResponse(
    @SerializedName("message")
    var message:String,
    @SerializedName("newObject")
    var newObject:Category
)

data class AddShopResponse(
    @SerializedName("code")
    var code:Int,
    @SerializedName("data")
    var data:DataAddShopResponse,
    @SerializedName("flag")
    var flag:Boolean
)

