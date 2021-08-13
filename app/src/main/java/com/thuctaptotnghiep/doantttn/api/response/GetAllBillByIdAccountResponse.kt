package com.thuctaptotnghiep.doantttn.api.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.sql.Date

data class Bill(
    @SerializedName("idBill")
    var idBill: String,
    @SerializedName("date")
    var date: Date,
    @SerializedName("status")
    var status:String,
    @SerializedName("AccountIdAccount")
    var idAccount:String
):Serializable

data class DataGetAllBillByIdAccount(
    @SerializedName("result")
    var result:List<Bill>
)

data class GetAllBillByIdAccountResponse(
    @SerializedName("code")
    var code:Int,
    @SerializedName("data")
    var data:DataGetAllBillByIdAccount,
    @SerializedName("flag")
    var flag:Boolean
)