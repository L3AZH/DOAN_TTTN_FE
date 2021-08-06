package com.thuctaptotnghiep.doantttn.api.response

import com.google.gson.annotations.SerializedName

data class DataDeletePriceListResponse(
    @SerializedName("message")
    var message:String
)

data class DeletePriceListResponse(
    @SerializedName("code")
    var code:Int,
    @SerializedName("data")
    var data:DataDeletePriceListResponse,
    @SerializedName("flag")
    var flag:Boolean
)