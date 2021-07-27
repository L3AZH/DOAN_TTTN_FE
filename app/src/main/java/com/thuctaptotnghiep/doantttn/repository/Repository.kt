package com.thuctaptotnghiep.doantttn.repository

import com.thuctaptotnghiep.doantttn.Constant
import com.thuctaptotnghiep.doantttn.api.ApiInterface
import com.thuctaptotnghiep.doantttn.api.request.*
import javax.inject.Inject

class Repository @Inject constructor(private val api: ApiInterface) {


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
    suspend fun createNewProduct(token: String, addProductRequest: AddProductRequest) =
        api.createNewProduct(token, addProductRequest)
    suspend fun deleteProduct(token: String, idProduct: String) =
        api.deleteProduct(token, idProduct)
    suspend fun updateProduct(
        token: String,
        idCategory: String,
        updateProductRequest: UpdateProductRequest
    ) = api.updateProduct(token, idCategory, updateProductRequest)
}