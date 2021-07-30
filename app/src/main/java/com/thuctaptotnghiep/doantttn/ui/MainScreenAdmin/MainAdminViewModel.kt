package com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin

import android.app.Application
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.thuctaptotnghiep.doantttn.App
import com.thuctaptotnghiep.doantttn.api.request.*
import com.thuctaptotnghiep.doantttn.api.response.*
import com.thuctaptotnghiep.doantttn.repository.Repository
import kotlinx.coroutines.*
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class MainAdminViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var repository: Repository

    var listCategory: MutableLiveData<List<Category>> = MutableLiveData()
    var listProduct: MutableLiveData<List<Product>> = MutableLiveData()
    var listShop: MutableLiveData<List<Shop>> = MutableLiveData()
    var listPriceList:MutableLiveData<List<PriceList>> = MutableLiveData()

    init {
        (application as App).getRepositoryComponent().inject(this)
    }

    fun getAllCategory(token: String) = CoroutineScope(Dispatchers.Default).launch {
        val response = repository.getAllCategory(token)
        if (response.isSuccessful) {
            listCategory.postValue(response.body()!!.data.result)
        }
    }

    fun createNewCategory(token: String, nameCategory: String): Deferred<Map<String, Any>> =
        CoroutineScope(Dispatchers.Default).async {
            val response = repository.createNewCategory(token, AddCategoryRequest(nameCategory))
            var resultMap: MutableMap<String, Any> = mutableMapOf()
            if (response.isSuccessful) {
                resultMap["flag"] = response.body()!!.flag
                resultMap["message"] = response.body()!!.data.message
            } else {
                val error =
                    ErrorResponse.convertErrorBodyToErrorResponseClass(response.errorBody()!!)
                resultMap["flag"] = error.flag
                resultMap["message"] = error.data.message
            }
            getAllCategory(token)
            resultMap
        }

    fun deleteCategory(token: String, idCategory: String): Deferred<Map<String, Any>> =
        CoroutineScope(Dispatchers.Default).async {
            val response = repository.deleteCategory(token, idCategory)
            var resultMap: MutableMap<String, Any> = mutableMapOf()
            if (response.isSuccessful) {
                resultMap["flag"] = response.body()!!.flag
                resultMap["message"] = response.body()!!.data.message
            } else {
                val error =
                    ErrorResponse.convertErrorBodyToErrorResponseClass(response.errorBody()!!)
                resultMap["flag"] = error.flag
                resultMap["message"] = error.data.message
            }
            getAllCategory(token)
            resultMap
        }

    fun updateCategory(
        token: String,
        idCategory: String,
        newCategoryname: String
    ): Deferred<Map<String, Any>> = CoroutineScope(Dispatchers.Default).async {
        val response =
            repository.updateCategory(token, idCategory, UpdateCategoryRequest(newCategoryname))
        var resultMap: MutableMap<String, Any> = mutableMapOf()
        if (response.isSuccessful) {
            resultMap["flag"] = response.body()!!.flag
            resultMap["message"] = response.body()!!.data.message
        } else {
            val error =
                ErrorResponse.convertErrorBodyToErrorResponseClass(response.errorBody()!!)
            resultMap["flag"] = error.flag
            resultMap["message"] = error.data.message
        }
        getAllCategory(token)
        resultMap
    }

    fun getAllProduct(token: String) = CoroutineScope(Dispatchers.Default).launch {
        val response = repository.getAllProduct(token)
        if (response.isSuccessful) {
            listProduct.postValue((response.body()!!.data.result))
        }
    }

    fun createNewProduct(
        token: String,
        nameProduct: String,
        idCategory: String
    ): Deferred<Map<String, Any>> =
        CoroutineScope(Dispatchers.Default).async {
            val response =
                repository.createNewProduct(token, AddProductRequest(nameProduct, idCategory))
            var resultMap: MutableMap<String, Any> = mutableMapOf()
            if (response.isSuccessful) {
                resultMap["flag"] = response.body()!!.flag
                resultMap["message"] = response.body()!!.data.message
            } else {
                val error =
                    ErrorResponse.convertErrorBodyToErrorResponseClass(response.errorBody()!!)
                resultMap["flag"] = error.flag
                resultMap["message"] = error.data.message
            }
            getAllProduct(token)
            resultMap
        }

    fun deleteProduct(
        token: String,
        idProduct: String
    ): Deferred<Map<String, Any>> = CoroutineScope(Dispatchers.Default).async {
        val response = repository.deleteProduct(token, idProduct)
        var resultMap: MutableMap<String, Any> = mutableMapOf()
        if (response.isSuccessful) {
            resultMap["flag"] = response.body()!!.flag
            resultMap["message"] = response.body()!!.data.message
        } else {
            val error =
                ErrorResponse.convertErrorBodyToErrorResponseClass(response.errorBody()!!)
            resultMap["flag"] = error.flag
            resultMap["message"] = error.data.message
        }
        getAllProduct(token)
        resultMap
    }

    fun updateProduct(
        token: String,
        idProduct: String,
        name: String,
        idCategory: String
    ): Deferred<Map<String, Any>> = CoroutineScope(Dispatchers.Default).async {
        val response =
            repository.updateProduct(token, idProduct, UpdateProductRequest(name, idCategory))
        var resultMap: MutableMap<String, Any> = mutableMapOf()
        if (response.isSuccessful) {
            resultMap["flag"] = response.body()!!.flag
            resultMap["message"] = response.body()!!.data.message
        } else {
            val error =
                ErrorResponse.convertErrorBodyToErrorResponseClass(response.errorBody()!!)
            resultMap["flag"] = error.flag
            resultMap["message"] = error.data.message
        }
        getAllProduct(token)
        resultMap
    }

    fun getAllShop(token: String) = CoroutineScope(Dispatchers.Default).launch {
        val response = repository.getAllShop(token)
        if (response.isSuccessful) {
            listShop.postValue(response.body()!!.data.result)
        }
    }

    fun createNewShop(
        token: String,
        name: String,
        phone: String,
        address: String
    ): Deferred<Map<String, Any>> = CoroutineScope(Dispatchers.Default).async {
        val response =
            repository.createNewShop(token, AddShopRequest(name, phone, address))
        var resultMap: MutableMap<String, Any> = mutableMapOf()
        if (response.isSuccessful) {
            resultMap["flag"] = response.body()!!.flag
            resultMap["message"] = response.body()!!.data.message
        } else {
            val error =
                ErrorResponse.convertErrorBodyToErrorResponseClass(response.errorBody()!!)
            resultMap["flag"] = error.flag
            resultMap["message"] = error.data.message
        }
        getAllShop(token)
        resultMap
    }

    fun deleteShop(
        token: String,
        idShop: String
    ): Deferred<Map<String,Any>> = CoroutineScope(Dispatchers.Default).async {
        val response = repository.deleteShop(token, idShop)
        var resultMap: MutableMap<String, Any> = mutableMapOf()
        if (response.isSuccessful) {
            resultMap["flag"] = response.body()!!.flag
            resultMap["message"] = response.body()!!.data.message
        } else {
            val error =
                ErrorResponse.convertErrorBodyToErrorResponseClass(response.errorBody()!!)
            resultMap["flag"] = error.flag
            resultMap["message"] = error.data.message
        }
        getAllShop(token)
        resultMap
    }

    fun updateShop(
        token: String,
        idShop: String,
        name: String,
        phone: String,
        address: String
    ): Deferred<Map<String, Any>> = CoroutineScope(Dispatchers.Default).async {
        val response =
            repository.updateShop(token, idShop, UpdateShopRequest(name, phone,address))
        var resultMap: MutableMap<String, Any> = mutableMapOf()
        if (response.isSuccessful) {
            resultMap["flag"] = response.body()!!.flag
            resultMap["message"] = response.body()!!.data.message
        } else {
            val error =
                ErrorResponse.convertErrorBodyToErrorResponseClass(response.errorBody()!!)
            resultMap["flag"] = error.flag
            resultMap["message"] = error.data.message
        }
        getAllShop(token)
        resultMap
    }


    fun getAllPriceList(token: String) = CoroutineScope(Dispatchers.Default).launch {
        val response = repository.getAllPriceList(token)
        if (response.isSuccessful) {
            listPriceList.postValue((response.body()!!.data.result))
        }
    }

    fun createNewPriceListObject(
        token: String,
        idShop: String,
        idProduct: String,
        price:Double,
        image:Bitmap
    ):Deferred<Map<String,Any>> = CoroutineScope(Dispatchers.Default).async {
        val stream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.PNG,50,stream)
        val imageByte = stream.toByteArray()
        Log.i("IMAGE", "${imageByte[0]} ${imageByte[1]} ${imageByte[2]}")
        val response =
            repository.createNewPriceListObject(token,
                AddPriceListObjectRequest(idShop,idProduct,price,imageByte)
            )
        var resultMap: MutableMap<String, Any> = mutableMapOf()
        if (response.isSuccessful) {
            resultMap["flag"] = response.body()!!.flag
            resultMap["message"] = response.body()!!.data.message
        } else {
            val error =
                ErrorResponse.convertErrorBodyToErrorResponseClass(response.errorBody()!!)
            resultMap["flag"] = error.flag
            resultMap["message"] = error.data.message
        }
        getAllPriceList(token)
        resultMap
    }

}