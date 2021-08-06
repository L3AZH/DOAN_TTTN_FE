package com.thuctaptotnghiep.doantttn.api.response

import com.google.gson.annotations.SerializedName

data class DatAddPriceListObject(
    @SerializedName("message")
    var message:String,
    @SerializedName("newObject")
    var newObject:Category
)

data class AddPriceListObjectResponse(
    @SerializedName("code")
    var code:Int,
    @SerializedName("data")
    var data:DatAddPriceListObject,
    @SerializedName("flag")
    var flag:Boolean
)
