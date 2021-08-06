package com.thuctaptotnghiep.doantttn.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "CartTable")
data class Cart(
    @PrimaryKey(autoGenerate = true)
    var idCart: Int,
    var idShop:String,
    var nameShop:String,
    var idProduct:String,
    var nameProduct:String,
    var price:Double,
    var amount:Int
)