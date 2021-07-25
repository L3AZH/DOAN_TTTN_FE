package com.thuctaptotnghiep.doantttn.api.response

import com.google.gson.annotations.SerializedName


data class Product(
    @SerializedName("idProduct")
    var idProduct:String,
    @SerializedName("name")
    var name:String,
    @SerializedName("CategoryIdCategory")
    var CategoryIdCategory:String
)

data class DataAllProductResponse(
    @SerializedName("result")
    var result:List<Product>
)

data class GetAllProductResponse(
    @SerializedName("code")
    var code:Int,
    @SerializedName("data")
    var data:DataAllProductResponse,
    @SerializedName("flag")
    var flag:Boolean
)