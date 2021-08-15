package com.thuctaptotnghiep.doantttn.api.request

import com.google.gson.annotations.SerializedName
import java.sql.Date

data class CreateNewBillRequest(
    @SerializedName("date")
    var date:String
)