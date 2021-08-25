package com.thuctaptotnghiep.doantttn.repository

import com.thuctaptotnghiep.doantttn.Constant
import com.thuctaptotnghiep.doantttn.api.ApiInterface
import com.thuctaptotnghiep.doantttn.api.request.*
import com.thuctaptotnghiep.doantttn.db.DbDao
import com.thuctaptotnghiep.doantttn.db.model.Cart
import javax.inject.Inject

class Repository @Inject constructor(private val api: ApiInterface, private val dbDao: DbDao) {


    suspend fun login(email: String, password: String) =
        api.login(Constant.getEncryptAccount(), LoginRequest(email, password))

    suspend fun register(
        email: String,
        password: String,
        role: String,
        phone: String,
        address: String
    ) =
        api.register(
            Constant.getEncryptAccount(),
            RegisterRequest(email, password, role, phone, address)
        )

    suspend fun refreshToken(refreshToken: String) =
        api.refreshToken(
            Constant.getEncryptAccount(),
            RefreshTokenRequest(refreshToken)
        )

    suspend fun checkTokenExpire(token: String) = api.checkTokenExpire(token)
    suspend fun changePassword(
        token: String,
        idAccount: String,
        changePasswordRequest: ChangePasswordRequest
    ) = api.changePassword(token, idAccount, changePasswordRequest)


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
    suspend fun getListProductByCategory(token: String, idCategory: String) =
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


    suspend fun getAllDetailShopProduct(token: String) = api.getAllDetailShopProduct(token)
    suspend fun getListDetailShopProductByIdProduct(token: String, idProduct: String) =
        api.getListDetailShopProductByIdProduct(token, idProduct)

    suspend fun getListDetailShopProductByNameProduct(
        token: String,
        nameProduct: String
    ) = api.getListDetailShopProductByNameProduct(token, nameProduct)

    suspend fun createNewDetailShopProductObject(
        token: String,
        addDetailShopProductObjectRequest: AddDetailShopProductObjectRequest
    ) = api.createNewDetailShopProductObject(token, addDetailShopProductObjectRequest)

    suspend fun deleteDetailShopProductObject(
        token: String,
        idShop: String,
        idProduct: String
    ) = api.deleteDetailShopProductObject(token, idShop, idProduct)

    suspend fun updateDetailShopProductObject(
        token: String,
        idShop: String,
        idProduct: String,
        updateDetailShopProductRequest: UpdateDetailShopProductRequest
    ) = api.updateDetailShopProductObject(token, idShop, idProduct, updateDetailShopProductRequest)

    suspend fun addToCart(cart: Cart) = dbDao.insertToCart(cart)
    suspend fun deleteCart(cart: Cart) = dbDao.deleteCart(cart)
    suspend fun updateCart(cart: Cart) = dbDao.updateCart(cart)
    suspend fun getAllCart(email: String) = dbDao.getAllCart(email)
    suspend fun checkCartExistInDb(idShop: String, idProduct: String, email: String) =
        dbDao.checkCartExistInDb(idShop, idProduct, email)


    suspend fun getAllBill(token: String) = api.getAllBill(token)
    suspend fun getBillByIdAccount(token: String, idAccount: String) =
        api.getBillByIdAccount(token, idAccount)

    suspend fun createNewBill(
        token: String,
        idAccount: String,
        createNewBillRequest: CreateNewBillRequest
    ) = api.createNewBill(token, idAccount, createNewBillRequest)

    suspend fun confrimBill(token: String, idBill: String) = api.confirmBill(token, idBill)


    suspend fun getBillDetailByIdBill(token: String, idBill: String) =
        api.getBillDetailByIdBill(token, idBill)

    suspend fun createNewListBillDetail(
        token: String,
        createNewListBillDetailRequest: CreateNewListBillDetailRequest
    ) = api.createNewListBillDetail(token, createNewListBillDetailRequest)
}