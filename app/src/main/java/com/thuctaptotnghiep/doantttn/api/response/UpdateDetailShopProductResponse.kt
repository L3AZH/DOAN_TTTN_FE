package com.thuctaptotnghiep.doantttn.api.response

import com.google.gson.annotations.SerializedName

data class DataUpdateDetailShopProduct(
    @SerializedName("message")
    var message:String
)

data class UpdateDetailShopProductResponse(
    @SerializedName("code")
    var code:Int,
    @SerializedName("data")
    var data:DataUpdateDetailShopProduct,
    @SerializedName("flag")
    var flag:Boolean
)