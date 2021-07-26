package com.thuctaptotnghiep.doantttn.api.request

import com.google.gson.annotations.SerializedName

data class UpdateCategoryRequest(
    @SerializedName("name")
    var name:String
)