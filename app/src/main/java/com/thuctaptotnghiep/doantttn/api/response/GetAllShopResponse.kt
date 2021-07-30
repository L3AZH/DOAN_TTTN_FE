package com.thuctaptotnghiep.doantttn.api.response

import com.google.gson.annotations.SerializedName

data class Shop(
    @SerializedName("idShop")
    var idShop:String,
    @SerializedName("name")
    var name:String,
    @SerializedName("phone")
    var phone:String,
    @SerializedName("address")
    var address:String
){
    override fun toString(): String {
        return name
    }
}

data class DataAllShopResponse(
    @SerializedName("result")
    var result: List<Shop>
)

data class GetAllShopResponse(
    @SerializedName("code")
    var code:Int,
    @SerializedName("data")
    var data: DataAllShopResponse,
    @SerializedName("flag")
    var flag:Boolean,
)