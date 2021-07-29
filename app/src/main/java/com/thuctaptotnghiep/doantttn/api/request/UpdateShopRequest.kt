package com.thuctaptotnghiep.doantttn.api.request

import com.google.gson.annotations.SerializedName

data class UpdateShopRequest(
    @SerializedName("name")
    var name:String,
    @SerializedName("phone")
    var phone:String,
    @SerializedName("address")
    var address:String
)