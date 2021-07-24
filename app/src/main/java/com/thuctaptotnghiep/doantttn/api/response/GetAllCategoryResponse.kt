package com.thuctaptotnghiep.doantttn.api.response

import com.google.gson.annotations.SerializedName


data class Category(
    @SerializedName("idCategory")
    var idCategory:String,
    @SerializedName("name")
    var name:String
)

data class DataAllCategoryResponse(
    @SerializedName("result")
    var result:List<Category>
)

data class GetAllCategoryResponse(
    @SerializedName("code")
    var code:Int,
    @SerializedName("data")
    var data:DataAllCategoryResponse,
    @SerializedName("flag")
    var flag:Boolean
)