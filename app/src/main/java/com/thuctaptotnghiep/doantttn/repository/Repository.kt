package com.thuctaptotnghiep.doantttn.repository

import com.thuctaptotnghiep.doantttn.Constant
import com.thuctaptotnghiep.doantttn.api.ApiInterface
import com.thuctaptotnghiep.doantttn.api.request.*
import com.thuctaptotnghiep.doantttn.db.DbDao
import com.thuctaptotnghiep.doantttn.db.model.Cart
import javax.inject.Inject

class Repository @Inject constructor(private val api: ApiInterface,private val dbDao: DbDao) {


    suspend fun login(email: String, password: String) =
        api.login(Constant.getEncryptAccount(), LoginRequest(email, password))
    suspend fun register(email: String, password: String, role: String) =
        api.register(
            Constant.getEncryptAccount(),
            RegisterRequest(email, password, role)
        )
    suspend fun refreshToken(refreshToken: String) =
        api.refreshToken(
            Constant.getEncryptAccount(),
            RefreshTokenRequest(refreshToken)
        )
    suspend fun checkTokenExpire(token: String) = api.checkTokenExpire(token)


    suspend fun getAllCategory(token: String) = api.getAllCategory(token)
    suspend fun createNewCategory(token: String, addCategoryRequest: AddCategoryRequest) =
        api.createNewCategory(token, addCategoryRequest)
    suspend fun deleteCategory(token: String, idCategory: String) =
        api.deleteCategory(token, idCategory)
    suspend fun updateCategory(
        token: String,
        idCategory: String,
        categoryRequest: UpdateCategoryRequest
    ) = api.updateCategory(token, idCategory, categoryRequest)


    suspend fun getAllProduct(token: String) = api.getAllProduct(token)
    suspend fun getListProductByCategory(token: String,idCategory: String) =
        api.getListProductByCategory(token, idCategory)
    suspend fun createNewProduct(token: String, addProductRequest: AddProductRequest) =
        api.createNewProduct(token, addProductRequest)
    suspend fun deleteProduct(token: String, idProduct: String) =
        api.deleteProduct(token, idProduct)
    suspend fun updateProduct(
        token: String,
        idCategory: String,
        updateProductRequest: UpdateProductRequest
    ) = api.updateProduct(token, idCategory, updateProductRequest)


    suspend fun getAllShop(token: String) = api.getAllShop(token)
    suspend fun createNewShop(token: String, addShopRequest: AddShopRequest) =
        api.createNewShop(token, addShopRequest)
    suspend fun deleteShop(token: String, idShop: String) = api.deleteShop(token, idShop)
    suspend fun updateShop(
        token: String,
        idShop: String,
        updateShopRequest: UpdateShopRequest
    ) = api.updateShop(token, idShop, updateShopRequest)


    suspend fun getAllPriceList(token: String) = api.getAllPriceList(token)
    suspend fun createNewPriceListObject(
        token: String,
        addPriceListObjectRequest: AddPriceListObjectRequest
    ) = api.createNewPriceListObject(token, addPriceListObjectRequest)
    suspend fun deletePriceListObject(
        token: String,
        idShop: String,
        idProduct: String
    ) = api.deletePriceListObject(token, idShop, idProduct)
    suspend fun updatePriceListObject(
        token: String,
        idShop: String,
        idProduct: String,
        updatePriceListRequest: UpdatePriceListRequest
    ) = api.updatePriceListObject(token,idShop,idProduct,updatePriceListRequest)

    suspend fun addToCart(cart: Cart) = dbDao.insertToCart(cart)
    suspend fun deleteCart(cart: Cart) = dbDao.deleteCart(cart)
    suspend fun updateCart(cart: Cart) = dbDao.updateCart(cart)
    suspend fun getAllCart() = dbDao.getAllCart()
}