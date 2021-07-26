package com.thuctaptotnghiep.doantttn.api.response

import com.google.gson.annotations.SerializedName

data class DataUpdateCategoryResponse(
    @SerializedName("message")
    var message:String
)

data class UpdateCategoryReponse(
    @SerializedName("code")
    var code:Int,
    @SerializedName("data")
    var data:DataDeleteCategoryResponse,
    @SerializedName("flag")
    var flag:Boolean
)