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
    @Query("select * from CartTable")
    suspend fun getAllCart():List<Cart>
}