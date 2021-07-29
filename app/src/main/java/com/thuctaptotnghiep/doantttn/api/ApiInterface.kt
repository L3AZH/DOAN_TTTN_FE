package com.thuctaptotnghiep.doantttn.api

import com.thuctaptotnghiep.doantttn.api.request.*
import com.thuctaptotnghiep.doantttn.api.response.*
import retrofit2.Response
import retrofit2.http.*


interface ApiInterface {


    @POST("auth/login")
    suspend fun login(
        @Header("Authorization") encryptAccount: String,
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @POST("auth/register")
    suspend fun register(
        @Header("Authorization") encryptAccount: String,
        @Body request: RegisterRequest
    ): Response<RegisterResponse>

    @POST("auth/refresh-token")
    suspend fun refreshToken(
        @Header("Authorization") encryptAccount: String,
        @Body request: RefreshTokenRequest
    ): Response<RefreshTokenResponse>

    @GET("auth/check-token-expire")
    suspend fun checkTokenExpire(
        @Header("Authorization") token:String
    ):Response<Any>

    /**
    API category
     */

    @GET("category/get-all-category")
    suspend fun getAllCategory(
        @Header("Authorization") token:String
    ):Response<GetAllCategoryResponse>

    @POST("category/create-new-category")
    suspend fun createNewCategory(
        @Header("Authorization") token: String,
        @Body categoryRequest: AddCategoryRequest
    ):Response<AddCategoryResponse>

    @DELETE("category/delete-category/{idCategory}")
    suspend fun deleteCategory(
        @Header("Authorization") token: String,
        @Path("idCategory") idCategory:String
    ):Response<DeleteCategoryReponse>

    @PUT("category/update-category/{idCategory}")
    suspend fun updateCategory(
        @Header("Authorization") token: String,
        @Path("idCategory") idCategory: String,
        @Body updateCategoryRequest: UpdateCategoryRequest
    ):Response<UpdateCategoryReponse>

    /**
    API Product
     */

    @GET("product/get-all-product")
    suspend fun getAllProduct(
        @Header("Authorization") token: String
    ):Response<GetAllProductResponse>

    @POST("product/create-new-product")
    suspend fun createNewProduct(
        @Header("Authorization") token: String,
        @Body addProductRequest:AddProductRequest
    ):Response<AddProductResponse>

    @DELETE("product/delete-product/{idProduct}")
    suspend fun deleteProduct(
        @Header("Authorization") token: String,
        @Path("idProduct") idProduct:String
    ):Response<DeleteProductResponse>

    @PUT("product/update-product/{idProduct}")
    suspend fun updateProduct(
        @Header("Authorization") token: String,
        @Path("idProduct") idProduct: String,
        @Body updateProductRequest: UpdateProductRequest
    ):Response<UpdateProductResponse>

    /**
    API shop
     */

    @GET("shop/get-all-shop")
    suspend fun getAllShop(
        @Header("Authorization") token:String
    ):Response<GetAllShopResponse>

    @POST("shop/create-new-shop")
    suspend fun createNewShop(
        @Header("Authorization") token: String,
        @Body addShopRequest: AddShopRequest
    ):Response<AddShopResponse>

    @DELETE("shop/delete-shop/{idShop}")
    suspend fun deleteShop(
        @Header("Authorization") token: String,
        @Path("idShop") idShop:String
    ):Response<DeleteShopResponse>

    @PUT("shop/update-shop/{id" +
            "Shop}")
    suspend fun updateShop(
        @Header("Authorization") token: String,
        @Path("idShop") idProduct: String,
        @Body updateShopRequest: UpdateShopRequest
    ):Response<UpdateShopResponse>

}