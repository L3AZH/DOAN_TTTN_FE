package com.thuctaptotnghiep.doantttn.ui.MainScreenGuest

import android.app.Application
import android.icu.util.Calendar
import android.os.Build
import android.text.format.DateFormat
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.thuctaptotnghiep.doantttn.App
import com.thuctaptotnghiep.doantttn.api.request.ChangePasswordRequest
import com.thuctaptotnghiep.doantttn.api.request.CreateNewBillRequest
import com.thuctaptotnghiep.doantttn.api.request.CreateNewListBillDetailRequest
import com.thuctaptotnghiep.doantttn.api.request.DataCreateNewListBillDetailRequest
import com.thuctaptotnghiep.doantttn.api.response.*
import com.thuctaptotnghiep.doantttn.db.model.Cart
import com.thuctaptotnghiep.doantttn.repository.Repository
import kotlinx.coroutines.*
import javax.inject.Inject

class MainGuestViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var repository: Repository

    var listCategory: MutableLiveData<List<Category>> = MutableLiveData()
    var listListDetailShopProductByProduct: MutableLiveData<List<DetailShopProductFullInformation>> =
        MutableLiveData()
    var listCart: MutableLiveData<List<Cart>> = MutableLiveData()
    var listBill: MutableLiveData<List<Bill>> = MutableLiveData()
    var listBillDetail: MutableLiveData<List<BillDetail>> = MutableLiveData()

    init {
        (application as App).getDataComponent().inject(this)
    }

    fun getAllCategory(token: String) = CoroutineScope(Dispatchers.Default).launch {
        val response = repository.getAllCategory(token)
        if (response.isSuccessful) {
            listCategory.postValue(response.body()!!.data.result)
        }
    }

    fun getListProductByCategory(token: String, idCategory: String): Deferred<List<Product>?> =
        CoroutineScope(Dispatchers.Default).async {
            val response = repository.getListProductByCategory(token, idCategory)
            if (response.isSuccessful) {
                response.body()!!.data.result
            } else {
                null
            }
        }

    fun getListPriceListByProduct(token: String, idProduct: String) =
        CoroutineScope(Dispatchers.Default).launch {
            val response = repository.getListDetailShopProductByIdProduct(token, idProduct)
            if (response.isSuccessful) {
                listListDetailShopProductByProduct.postValue(response.body()!!.data.result)
            }
        }

    fun getListPriceListByNameProduct(token: String, nameProduct: String) =
        CoroutineScope(Dispatchers.Default).launch {
            val resposne = repository.getListDetailShopProductByNameProduct(
                token,
                nameProduct
            )
            if (resposne.isSuccessful) {
                listListDetailShopProductByProduct.postValue(resposne.body()!!.data.result)
            }
        }

    fun clearListPriceListByProduct() {
        listListDetailShopProductByProduct.postValue(emptyList())
    }

    fun addToCart(
        detailShopProductFullInformation: DetailShopProductFullInformation,
        amount: Int,
        email: String
    ): Deferred<Map<String, Any>> =
        CoroutineScope(Dispatchers.Default).async {
            var resultMap: MutableMap<String, Any> = mutableMapOf()
            val newCart = Cart(
                null,
                email,
                detailShopProductFullInformation.idShop,
                detailShopProductFullInformation.nameShop,
                detailShopProductFullInformation.idProduct,
                detailShopProductFullInformation.nameProduct,
                detailShopProductFullInformation.price,
                amount,
                detailShopProductFullInformation.image.data
            )
            try {
                var cartExist = repository.checkCartExistInDb(
                    detailShopProductFullInformation.idShop,
                    detailShopProductFullInformation.idProduct,
                    email
                )
                if (cartExist == null) {
                    repository.addToCart(newCart)
                } else {
                    val newAmount = cartExist.amount + amount
                    if (newAmount > 10) {
                        resultMap["flag"] = false
                        resultMap["message"] = "Amount of product cant be greater than 10"
                        return@async resultMap
                    } else {
                        cartExist.amount = newAmount
                    }
                    repository.updateCart(cartExist)
                }
                resultMap["flag"] = true
                resultMap["message"] = "Add to cart Successfully"
                resultMap
            } catch (ex: Exception) {
                ex.printStackTrace()
                resultMap["flag"] = true
                resultMap["message"] = "Add to cart Fail"
                resultMap
            }
        }

    fun getListCart(email: String) = CoroutineScope(Dispatchers.Default).launch {
        listCart.postValue(repository.getAllCart(email))
    }

    fun deleteCart(cart: Cart, email: String) = CoroutineScope(Dispatchers.Default).launch {
        repository.deleteCart(cart)
        getListCart(email)
    }

    fun updateCartWhenMinus(cart: Cart, email: String) =
        CoroutineScope(Dispatchers.Default).launch {
            if (cart.amount > 0) {
                cart.amount = cart.amount - 1
                if (cart.amount == 0) {
                    repository.deleteCart(cart)
                } else {
                    repository.updateCart(cart)
                }
                getListCart(email)
            }
        }

    fun updateCartWhenPlus(cart: Cart, email: String) =
        CoroutineScope(Dispatchers.Default).launch {
            if (cart.amount < 10) {
                cart.amount = cart.amount + 1
                if (cart.amount <= 10) {
                    repository.updateCart(cart)
                    getListCart(email)
                }
            }
        }

    fun getBillByIdAccount(token: String, idAccount: String) =
        CoroutineScope(Dispatchers.Default).launch {
            val response = repository.getBillByIdAccount(token, idAccount)
            if (response.isSuccessful) {
                listBill.postValue(response.body()!!.data.result)
            } else {
                listBill.postValue(emptyList())
            }
        }

    fun getBillDetailByIdBill(token: String, idBill: String) =
        CoroutineScope(Dispatchers.Default).launch {
            val response = repository.getBillDetailByIdBill(token, idBill)
            if (response.isSuccessful) {
                listBillDetail.postValue(response.body()!!.data.result)
            } else {
                listBillDetail.postValue(emptyList())
            }
        }

    @RequiresApi(Build.VERSION_CODES.N)
    fun createBill(token: String, idAccount: String, email: String): Deferred<Map<String, Any>> =
        CoroutineScope(Dispatchers.Default).async {
            val result: MutableMap<String, Any> = mutableMapOf()
            val responseCreateBill = repository.createNewBill(
                token,
                idAccount,
                CreateNewBillRequest(
                    DateFormat.format(
                        "yyyy-MM-dd hh:mm:ss",
                        Calendar.getInstance().time
                    ).toString()
                )
            )
            if (responseCreateBill.isSuccessful) {
                val idBillJustCreated = responseCreateBill.body()!!.data.idBill
                var listDetailBill: MutableList<DataCreateNewListBillDetailRequest> =
                    mutableListOf()
                for (itemCart in listCart.value!!) {
                    listDetailBill.add(
                        DataCreateNewListBillDetailRequest(
                            idBillJustCreated,
                            itemCart.idProduct,
                            itemCart.idShop,
                            itemCart.amount
                        )
                    )
                    repository.deleteCart(itemCart)
                }
                listCart.postValue(repository.getAllCart(email))
                val responseCreateBillDetail = repository.createNewListBillDetail(
                    token,
                    CreateNewListBillDetailRequest(listDetailBill)
                )
                if (responseCreateBillDetail.isSuccessful) {
                    result["flag"] = responseCreateBillDetail.body()!!.flag
                    result["message"] = "Create Bill Successfully !!"
                } else {
                    val errorResponse =
                        ErrorResponse.convertErrorBodyToErrorResponseClass(responseCreateBillDetail.errorBody()!!)
                    result["flag"] = errorResponse.flag
                    result["message"] = errorResponse.data.message
                }
            } else {
                val errorResponse =
                    ErrorResponse.convertErrorBodyToErrorResponseClass(responseCreateBill.errorBody()!!)
                result["flag"] = errorResponse.flag
                result["message"] = errorResponse.data.message
            }
            result
        }

    fun clearListBillDetail() {
        listBillDetail.postValue(emptyList())
    }

    fun validationBeforeRegister(password: String,confirmPassword:String):Boolean{
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

}