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
    var listDetailShopProduct: MutableLiveData<List<DetailShopProduct>> = MutableLiveData()
    var listBill: MutableLiveData<List<Bill>> = MutableLiveData()
    var listDetailBill: MutableLiveData<List<BillDetail>> = MutableLiveData()

    init {
        (application as App).getDataComponent().inject(this)
    }

    fun getAllCategory(token: String) = CoroutineScope(Dispatchers.Default).launch {
        val response = repository.getAllCategory(token)
        if (response.isSuccessful) {
            listCategory.postValue(response.body()!!.data.result)
        } else {
            val error = ErrorResponse.convertErrorBodyToErrorResponseClass(response.errorBody()!!)
            if (error.code == 404 && error.data.message.equals(
                    "Can't find any category in database",
                    true
                )
            ) {
                listCategory.postValue(emptyList())
            }
        }
    }

    fun createNewCategory(token: String, nameCategory: String): Deferred<Map<String, Any>> =
        CoroutineScope(Dispatchers.Default).async {
            val response = repository.createNewCategory(token, AddCategoryRequest(nameCategory))
            val resultMap: MutableMap<String, Any> = mutableMapOf()
            if (response.isSuccessful) {
                resultMap["flag"] = response.body()!!.flag
                resultMap["message"] = response.body()!!.data.message
                getAllCategory(token)
            } else {
                val error =
                    ErrorResponse.convertErrorBodyToErrorResponseClass(response.errorBody()!!)
                resultMap["flag"] = error.flag
                resultMap["message"] = error.data.message
            }
            resultMap
        }

    fun deleteCategory(token: String, idCategory: String): Deferred<Map<String, Any>> =
        CoroutineScope(Dispatchers.Default).async {
            val response = repository.deleteCategory(token, idCategory)
            val resultMap: MutableMap<String, Any> = mutableMapOf()
            if (response.isSuccessful) {
                resultMap["flag"] = response.body()!!.flag
                resultMap["message"] = response.body()!!.data.message
                getAllCategory(token)
            } else {
                val error =
                    ErrorResponse.convertErrorBodyToErrorResponseClass(response.errorBody()!!)
                resultMap["flag"] = error.flag
                resultMap["message"] = error.data.message
            }
            resultMap
        }

    fun updateCategory(
        token: String,
        idCategory: String,
        newCategoryname: String
    ): Deferred<Map<String, Any>> = CoroutineScope(Dispatchers.Default).async {
        val response =
            repository.updateCategory(token, idCategory, UpdateCategoryRequest(newCategoryname))
        val resultMap: MutableMap<String, Any> = mutableMapOf()
        if (response.isSuccessful) {
            resultMap["flag"] = response.body()!!.flag
            resultMap["message"] = response.body()!!.data.message
            getAllCategory(token)
        } else {
            val error =
                ErrorResponse.convertErrorBodyToErrorResponseClass(response.errorBody()!!)
            resultMap["flag"] = error.flag
            resultMap["message"] = error.data.message
        }
        resultMap
    }

    fun getAllProduct(token: String) = CoroutineScope(Dispatchers.Default).launch {
        val response = repository.getAllProduct(token)
        if (response.isSuccessful) {
            listProduct.postValue((response.body()!!.data.result))
        } else {
            val error = ErrorResponse.convertErrorBodyToErrorResponseClass(response.errorBody()!!)
            if (error.code == 404 && error.data.message.equals(
                    "Can't find any Product in database !!",
                    true
                )
            ) {
                listProduct.postValue(emptyList())
            }
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
            val resultMap: MutableMap<String, Any> = mutableMapOf()
            if (response.isSuccessful) {
                resultMap["flag"] = response.body()!!.flag
                resultMap["message"] = response.body()!!.data.message
                getAllProduct(token)
            } else {
                val error =
                    ErrorResponse.convertErrorBodyToErrorResponseClass(response.errorBody()!!)
                resultMap["flag"] = error.flag
                resultMap["message"] = error.data.message
            }
            resultMap
        }

    fun deleteProduct(
        token: String,
        idProduct: String
    ): Deferred<Map<String, Any>> = CoroutineScope(Dispatchers.Default).async {
        val response = repository.deleteProduct(token, idProduct)
        val resultMap: MutableMap<String, Any> = mutableMapOf()
        if (response.isSuccessful) {
            resultMap["flag"] = response.body()!!.flag
            resultMap["message"] = response.body()!!.data.message
            getAllProduct(token)
        } else {
            val error =
                ErrorResponse.convertErrorBodyToErrorResponseClass(response.errorBody()!!)
            resultMap["flag"] = error.flag
            resultMap["message"] = error.data.message
        }
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
        val resultMap: MutableMap<String, Any> = mutableMapOf()
        if (response.isSuccessful) {
            resultMap["flag"] = response.body()!!.flag
            resultMap["message"] = response.body()!!.data.message
            getAllProduct(token)
        } else {
            val error =
                ErrorResponse.convertErrorBodyToErrorResponseClass(response.errorBody()!!)
            resultMap["flag"] = error.flag
            resultMap["message"] = error.data.message
        }
        resultMap
    }

    fun getAllShop(token: String) = CoroutineScope(Dispatchers.Default).launch {
        val response = repository.getAllShop(token)
        if (response.isSuccessful) {
            listShop.postValue(response.body()!!.data.result)
        }
        else{
            val error = ErrorResponse.convertErrorBodyToErrorResponseClass(response.errorBody()!!)
            if (error.code == 404 && error.data.message.equals(
                    "Can't find any shop in database",
                    true
                )
            ) {
                listShop.postValue(emptyList())
            }
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
        val resultMap: MutableMap<String, Any> = mutableMapOf()
        if (response.isSuccessful) {
            resultMap["flag"] = response.body()!!.flag
            resultMap["message"] = response.body()!!.data.message
            getAllShop(token)
        } else {
            val error =
                ErrorResponse.convertErrorBodyToErrorResponseClass(response.errorBody()!!)
            resultMap["flag"] = error.flag
            resultMap["message"] = error.data.message
        }
        resultMap
    }

    fun deleteShop(
        token: String,
        idShop: String
    ): Deferred<Map<String, Any>> = CoroutineScope(Dispatchers.Default).async {
        val response = repository.deleteShop(token, idShop)
        val resultMap: MutableMap<String, Any> = mutableMapOf()
        if (response.isSuccessful) {
            resultMap["flag"] = response.body()!!.flag
            resultMap["message"] = response.body()!!.data.message
            getAllShop(token)
        } else {
            val error =
                ErrorResponse.convertErrorBodyToErrorResponseClass(response.errorBody()!!)
            resultMap["flag"] = error.flag
            resultMap["message"] = error.data.message
        }
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
            repository.updateShop(token, idShop, UpdateShopRequest(name, phone, address))
        val resultMap: MutableMap<String, Any> = mutableMapOf()
        if (response.isSuccessful) {
            resultMap["flag"] = response.body()!!.flag
            resultMap["message"] = response.body()!!.data.message
            getAllShop(token)
        } else {
            val error =
                ErrorResponse.convertErrorBodyToErrorResponseClass(response.errorBody()!!)
            resultMap["flag"] = error.flag
            resultMap["message"] = error.data.message
        }
        resultMap
    }


    fun getAllDetailShopProduct(token: String) = CoroutineScope(Dispatchers.Default).launch {
        val response = repository.getAllDetailShopProduct(token)
        if (response.isSuccessful) {
            listDetailShopProduct.postValue((response.body()!!.data.result))
        } else {
            val error = ErrorResponse.convertErrorBodyToErrorResponseClass(response.errorBody()!!)
            if (error.code == 404 && error.data.message.equals(
                    "Can't find any DetailShopProduct in database",
                    true
                )
            ) {
                listDetailShopProduct.postValue(emptyList())
            }
        }
    }

    fun createNewDetailShopProductObject(
        token: String,
        idShop: String,
        idProduct: String,
        price: Double,
        image: Bitmap
    ): Deferred<Map<String, Any>> = CoroutineScope(Dispatchers.Default).async {
        val stream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.PNG, 50, stream)
        val imageByte = stream.toByteArray()
        val response =
            repository.createNewDetailShopProductObject(
                token,
                AddDetailShopProductObjectRequest(idShop, idProduct, price, imageByte)
            )
        val resultMap: MutableMap<String, Any> = mutableMapOf()
        if (response.isSuccessful) {
            resultMap["flag"] = response.body()!!.flag
            resultMap["message"] = response.body()!!.data.message
            getAllDetailShopProduct(token)
        } else {
            val error =
                ErrorResponse.convertErrorBodyToErrorResponseClass(response.errorBody()!!)
            resultMap["flag"] = error.flag
            resultMap["message"] = error.data.message
        }
        resultMap
    }

    fun deleteDetailShopProductObject(
        token: String,
        idShop: String,
        idProduct: String
    ): Deferred<Map<String, Any>> =
        CoroutineScope(Dispatchers.Default).async {
            val response = repository.deleteDetailShopProductObject(token, idShop, idProduct)
            val resultMap: MutableMap<String, Any> = mutableMapOf()
            if (response.isSuccessful) {
                resultMap["flag"] = response.body()!!.flag
                resultMap["message"] = response.body()!!.data.message
                getAllDetailShopProduct(token)
            } else {
                val error =
                    ErrorResponse.convertErrorBodyToErrorResponseClass(response.errorBody()!!)
                resultMap["flag"] = error.flag
                resultMap["message"] = error.data.message
            }
            resultMap
        }

    fun updateDetailShopProductObject(
        token: String,
        idShop: String,
        idProduct: String,
        price: Double,
        image: Bitmap
    ): Deferred<Map<String, Any>> =
        CoroutineScope(Dispatchers.Default).async {
            val stream = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.PNG, 50, stream)
            val imageByte = stream.toByteArray()
            val response =
                repository.updateDetailShopProductObject(
                    token,
                    idShop,
                    idProduct,
                    UpdateDetailShopProductRequest(price, imageByte)
                )
            val resultMap: MutableMap<String, Any> = mutableMapOf()
            if (response.isSuccessful) {
                resultMap["flag"] = response.body()!!.flag
                resultMap["message"] = response.body()!!.data.message
                getAllDetailShopProduct(token)
            } else {
                val error =
                    ErrorResponse.convertErrorBodyToErrorResponseClass(response.errorBody()!!)
                resultMap["flag"] = error.flag
                resultMap["message"] = error.data.message
            }
            //Log.i("TEST L4AZH", listDetailShopProduct.value!!.elementAt(0).price.toString())
            resultMap
        }

    fun getAllBill(token: String) = CoroutineScope(Dispatchers.Default).launch {
        val response = repository.getAllBill(token)
        if (response.isSuccessful) {
            listBill.postValue(response.body()!!.data.result)
        }
    }

    fun confirmBill(token: String, idBill: String): Deferred<Map<String, Any>> =
        CoroutineScope(Dispatchers.Default).async {
            val result: MutableMap<String, Any> = mutableMapOf()
            val resposne = repository.confrimBill(token, idBill)
            if (resposne.isSuccessful) {
                result["flag"] = resposne.body()!!.flag
                result["message"] = resposne.body()!!.data.message
                getAllBill(token)
            } else {
                val error =
                    ErrorResponse.convertErrorBodyToErrorResponseClass(resposne.errorBody()!!)
                result["flag"] = error.flag
                result["message"] = error.data.message
            }
            result
        }

    fun getAllListDetailBill(token: String, idBill: String) =
        CoroutineScope(Dispatchers.Default).launch {
            val response = repository.getBillDetailByIdBill(token, idBill)
            if (response.isSuccessful) {
                listDetailBill.postValue(response.body()!!.data.result)
            }
        }

    fun registerAdminAccount(
        email: String,
        password: String,
        phone: String,
        address: String
    ): Deferred<Map<String, Any>> =
        CoroutineScope(Dispatchers.Default).async {
            val resultMap: MutableMap<String, Any> = mutableMapOf()
            val resposne = repository.register(email, password, "admin", phone, address)
            if (resposne.isSuccessful) {
                resultMap["flag"] = resposne.body()!!.flag
                resultMap["message"] = resposne.body()!!.data.message
            } else {
                val error =
                    ErrorResponse.convertErrorBodyToErrorResponseClass(resposne.errorBody()!!)
                resultMap["flag"] = error.flag
                resultMap["message"] = error.data.message
            }
            resultMap
        }

    fun validationBeforeRegister(password: String, confirmPassword: String): Boolean {
        return (password == confirmPassword)
    }

    fun changePassword(
        token: String,
        idAccount: String,
        oldPassword: String,
        confirmPassword: String
    ): Deferred<Map<String, Any>> =
        CoroutineScope(Dispatchers.Default).async {
            val resultMap: MutableMap<String, Any> = mutableMapOf()
            val response = repository.changePassword(
                token, idAccount,
                ChangePasswordRequest(oldPassword, confirmPassword)
            )
            if (response.isSuccessful) {
                resultMap["flag"] = response.body()!!.flag
                resultMap["message"] = response.body()!!.data.message
            } else {
                val error =
                    ErrorResponse.convertErrorBodyToErrorResponseClass(response.errorBody()!!)
                resultMap["flag"] = error.flag
                resultMap["message"] = error.data.message
            }
            resultMap
        }

    fun clearListDetailBill() {
        listDetailBill.postValue(emptyList())
    }

    fun clearListPriceList() {
        listDetailShopProduct.postValue(emptyList())
    }


}