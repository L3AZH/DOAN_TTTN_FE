package com.thuctaptotnghiep.doantttn.api.request

import com.google.gson.annotations.SerializedName

data class UpdateProductRequest(
    @SerializedName("name")
    var name:String,
    @SerializedName("idCategory")
    var idCategory:String,
)