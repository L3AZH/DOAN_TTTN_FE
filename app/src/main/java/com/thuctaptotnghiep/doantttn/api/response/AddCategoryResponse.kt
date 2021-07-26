package com.thuctaptotnghiep.doantttn.api.response

import com.google.gson.annotations.SerializedName

data class DataAddCategoryResponse(
    @SerializedName("message")
    var message:String,
    @SerializedName("newObject")
    var newObject:Category
)

data class AddCategoryResponse(
    @SerializedName("code")
    var code:Int,
    @SerializedName("data")
    var data:DataAddCategoryResponse,
    @SerializedName("flag")
    var flag:Boolean
)