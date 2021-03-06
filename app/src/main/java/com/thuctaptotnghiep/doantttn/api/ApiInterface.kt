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

    @PUT("auth/change-password/{idAccount}")
    suspend fun changePassword(
        @Header("Authorization") token: String,
        @Path("idAccount") idAccount:String,
        @Body changePasswordRequest: ChangePasswordRequest
    ):Response<ChangePasswordResponse>

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

    @GET("product/get-product-by-id-category/{idCategory}")
    suspend fun getListProductByCategory(
        @Header("Authorization") token: String,
        @Path("idCategory") idCategory:String
    ):Response<GetListProductByCategoryResponse>

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

    /**
     * API PriceList
     */
    @GET("detailshopproduct/get-all-detail-shop-product")
    suspend fun getAllDetailShopProduct(
        @Header("Authorization") token: String
    ):Response<GetAllDetailShopProductResponse>

    @GET("detailshopproduct/get-shop-by-id-product/{idProduct}")
    suspend fun getListDetailShopProductByIdProduct(
        @Header("Authorization") token: String,
        @Path("idProduct") idProduct: String
    ):Response<GetListDetailShopProductByProductResponse>

    /**
     * GET method cant have a param like @Body, @Body will make error like non HTTP cant have a body
     */

    @GET("detailshopproduct/get-list-detail-shop-product-by-name-product/{nameProduct}")
    suspend fun getListDetailShopProductByNameProduct(
        @Header("Authorization") token: String,
        @Path("nameProduct") nameProduct:String
    ):Response<GetListDetailShopProductByNameProductResponse>

    @POST("detailshopproduct/create-new-detail-shop-product-object")
    suspend fun createNewDetailShopProductObject(
        @Header("Authorization") token: String,
        @Body addDetailShopProductObjectRequest: AddDetailShopProductObjectRequest
    ):Response<AddDetailShopProductObjectResponse>

    @DELETE("detailshopproduct/delete-detail-shop-product-object/{idShop}/{idProduct}")
    suspend fun deleteDetailShopProductObject(
        @Header("Authorization") token: String,
        @Path("idShop") idShop:String,
        @Path("idProduct") idProduct: String
    ):Response<DeleteDetailShopProductResponse>

    @PUT("detailshopproduct/update-detail-shop-product-object/{idShop}/{idProduct}")
    suspend fun updateDetailShopProductObject(
        @Header("Authorization") token: String,
        @Path("idShop") idShop: String,
        @Path("idProduct") idProduct: String,
        @Body updateDetailShopProductRequest: UpdateDetailShopProductRequest
    ):Response<UpdateDetailShopProductResponse>

    /**
     * API Bill
     */
    @GET("bill/get-all-bill")
    suspend fun getAllBill(
        @Header("Authorization") token: String,
    ):Response<GetAllBillResponse>

    @GET("bill/get-bill-by-id-account/{idAccount}")
    suspend fun getBillByIdAccount(
        @Header("Authorization") token: String,
        @Path("idAccount") idAccount:String
    ):Response<GetAllBillByIdAccountResponse>

    @POST("bill/create-new-bill/{idAccount}")
    suspend fun createNewBill(
        @Header("Authorization") token: String,
        @Path("idAccount") idAccount: String,
        @Body createNewBillRequest: CreateNewBillRequest
    ):Response<CreateNewBillResponse>

    @PUT("bill/update-bill/{idBill}")
    suspend fun confirmBill(
        @Header("Authorization") token: String,
        @Path("idBill") idBill: String
    ):Response<UpdateBillResponse>

    /**
     * API Bill Detail
     */
    @GET("billdetail/get-bill-detail/{idBill}")
    suspend fun getBillDetailByIdBill(
        @Header("Authorization") token: String,
        @Path("idBill") idBill:String
    ):Response<GetAllBillDetailByIdBillResponse>

    @POST("billdetail/create-new-list-bill-detail")
    suspend fun createNewListBillDetail(
        @Header("Authorization") token: String,
        @Body createNewListBillDetailRequest: CreateNewListBillDetailRequest
    ):Response<CreateNewListBillDetailResponse>

}