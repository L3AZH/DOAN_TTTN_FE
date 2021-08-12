package com.thuctaptotnghiep.doantttn.db

import androidx.room.*
import com.thuctaptotnghiep.doantttn.db.model.Cart

@Dao
interface DbDao {
    @Insert
    suspend fun insertToCart(cart: Cart)

    @Delete
    suspend fun deleteCart(cart: Cart)

    @Update
    suspend fun updateCart(cart: Cart)

    @Query("select * from CartTable where email = :email")
    suspend fun getAllCart(email: String): List<Cart>

    @Query("select * from CartTable where idShop = :idShop and idProduct = :idProduct and email = :email")
    suspend fun checkCartExistInDb(idShop: String, idProduct: String, email: String): Cart?
}