package com.thuctaptotnghiep.doantttn.api.response

import com.google.gson.annotations.SerializedName

data class DataUpdatePriceList(
    @SerializedName("message")
    var message:String
)

data class UpdatePriceListResponse(
    @SerializedName("code")
    var code:Int,
    @SerializedName("data")
    var data:DataUpdatePriceList,
    @SerializedName("flag")
    var flag:Boolean
)