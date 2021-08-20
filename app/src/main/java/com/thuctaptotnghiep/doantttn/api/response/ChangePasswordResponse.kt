package com.thuctaptotnghiep.doantttn.api.response

import com.google.gson.annotations.SerializedName

data class DataChangePassword(
    @SerializedName("message")
    var message:String
)

class ChangePasswordResponse (
    @SerializedName("code")
    var code:Int,
    @SerializedName("data")
    var data:DataChangePassword,
    @SerializedName("flag")
    var flag:Boolean
)