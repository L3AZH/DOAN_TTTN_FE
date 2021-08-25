package com.thuctaptotnghiep.doantttn.api.response

import com.google.gson.annotations.SerializedName

data class DataAddDetailShopProductObject(
    @SerializedName("message")
    var message:String,
    @SerializedName("newObject")
    var newObject:Category
)

data class AddDetailShopProductObjectResponse(
    @SerializedName("code")
    var code:Int,
    @SerializedName("data")
    var data:DataAddDetailShopProductObject,
    @SerializedName("flag")
    var flag:Boolean
)
