package com.thuctaptotnghiep.doantttn.dialog

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.thuctaptotnghiep.doantttn.utils.Constant
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.adapter.ProductArrayAdapter
import com.thuctaptotnghiep.doantttn.adapter.ShopArrayAdapter
import com.thuctaptotnghiep.doantttn.api.response.DetailShopProduct
import com.thuctaptotnghiep.doantttn.api.response.Product
import com.thuctaptotnghiep.doantttn.api.response.Shop
import com.thuctaptotnghiep.doantttn.databinding.DetailShopProductEditDialogBinding
import com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.MainAdminActivity
import com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.MainAdminViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailShopProductEditDialog(val detailShopProduct: DetailShopProduct) : DialogFragment() {

    lateinit var binding: DetailShopProductEditDialogBinding
    lateinit var viewModel: MainAdminViewModel
    lateinit var shopArrayAdapter: ShopArrayAdapter
    lateinit var productArrayAdapter: ProductArrayAdapter
    lateinit var selectedShop: Shop
    lateinit var selectedProduct: Product

    lateinit var onResultLauncher: ActivityResultLauncher<Intent>
    lateinit var onRequestLauncher: ActivityResultLauncher<String>

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val layoutInflater = requireActivity().layoutInflater
            binding = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.detail_shop_product_edit_dialog,
                null,
                false
            )
            viewModel = (requireActivity() as MainAdminActivity).viewModel

            setStartActivityForResult()
            setOnRequestPermisson()

            setUpBinding()
            setUpShopArrayAdapter()
            setUpProductArrayAdapter()
            setOnClickChooseImageBtn()
            setOnClickDeleteBtn()
            setOnClickUpdateBtn()
            setOnclickCancelBtn()

            builder.setView(binding.root)
            builder.create()

        } ?: throw  IllegalStateException("Activity must not empty")
    }

    fun setUpBinding() {
        binding.priceEditTextInputEditText.setText(String.format("%f",detailShopProduct.price))
        binding.imageProductOfShopEdit.setImageBitmap(
            BitmapFactory.decodeByteArray(
                detailShopProduct.image.data,
                0,
                detailShopProduct.image.data.size
            )
        )
    }

    fun setUpShopArrayAdapter() {
        shopArrayAdapter = ShopArrayAdapter(requireContext(), viewModel.listShop.value!!)
        (binding.shopEditSpinnervalue as AutoCompleteTextView).setAdapter(shopArrayAdapter)
        (binding.shopEditSpinnervalue as AutoCompleteTextView).onItemClickListener =
            object : AdapterView.OnItemClickListener {
                override fun onItemClick(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedShop = shopArrayAdapter.getItem(position)!!
                }
            }
        selectedShop = shopArrayAdapter.getItemId(detailShopProduct.shopIdShop)!!
        binding.shopEditSpinnervalue.setText(selectedShop.name, false)
        /**
         * dismiss drop down
         */
        (binding.shopEditSpinnervalue as AutoCompleteTextView).dropDownHeight = 0
    }

    fun setUpProductArrayAdapter() {
        productArrayAdapter = ProductArrayAdapter(requireContext(), viewModel.listProduct.value!!)
        (binding.productEditSpinnerValue as AutoCompleteTextView).setAdapter(productArrayAdapter)
        (binding.productEditSpinnerValue as AutoCompleteTextView).onItemClickListener =
            object : AdapterView.OnItemClickListener {
                override fun onItemClick(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedProduct = productArrayAdapter.getItem(position)!!
                }
            }
        selectedProduct = productArrayAdapter.getItemId(detailShopProduct.productIdProduct)!!
        binding.productEditSpinnerValue.setText(selectedProduct.name,false)
        (binding.productEditSpinnerValue as AutoCompleteTextView).dropDownHeight = 0
    }

    fun setOnClickDeleteBtn() {
        binding.deletePriceListEditDialogBtn.setOnClickListener {
            val prefs = requireActivity().getSharedPreferences(
                Constant.SHARE_PREFERENCE_NAME,
                Context.MODE_PRIVATE
            )
            val token = prefs.getString("token", "null")!!
            CoroutineScope(Dispatchers.Default).launch {
                val result = viewModel.deleteDetailShopProductObject(
                    token,
                    detailShopProduct.shopIdShop,
                    detailShopProduct.productIdProduct
                ).await()
                if ((result["flag"] as Boolean)) {
                    val dialogInform = InformDialog("success", result["message"].toString())
                    dialogInform.show(requireActivity().supportFragmentManager, "inform dialog")
                    dialog?.cancel()
                } else {
                    val dialogInform = InformDialog("fail", result["message"].toString())
                    dialogInform.show(requireActivity().supportFragmentManager, "inform dialog")
                }
            }
        }
    }

    fun setOnClickUpdateBtn() {
        binding.savePriceListEditDialogBtn.setOnClickListener {
            val prefs = requireActivity().getSharedPreferences(
                Constant.SHARE_PREFERENCE_NAME,
                Context.MODE_PRIVATE
            )
            val token = prefs.getString("token", "null")
            if (binding.imageProductOfShopEdit.drawable == null) {
                val dialoginform = InformDialog("fail", "Please choose image before input")
                dialoginform.show(requireActivity().supportFragmentManager, "dialog inform")
            } else if (!checkPricevalid()) {
                val dialoginform = InformDialog("fail", "Please enter price")
                dialoginform.show(requireActivity().supportFragmentManager, "dialog inform")
            } else {
                CoroutineScope(Dispatchers.Default).launch {
                    val loadingDialog = LoadingDialog()
                    loadingDialog.show(requireActivity().supportFragmentManager, "loading dialog")
                    val result = viewModel.updateDetailShopProductObject(
                        token!!,
                        selectedShop.idShop,
                        selectedProduct.idProduct,
                        binding.priceEditTextInputEditText.text.toString().toDouble(),
                        (binding.imageProductOfShopEdit.drawable as BitmapDrawable).bitmap
                    ).await()
                    loadingDialog.cancelLoading()
                    if ((result["flag"] as Boolean)) {
                        val dialoginform = InformDialog("success", result["message"].toString())
                        dialoginform.show(requireActivity().supportFragmentManager, "dialog inform")
                        dialog?.cancel()
                    } else {
                        val dialoginform = InformDialog("fail", result["message"].toString())
                        dialoginform.show(requireActivity().supportFragmentManager, "dialog inform")
                    }
                }
            }
        }
    }

    fun checkPricevalid(): Boolean {
        try {
            binding.priceEditTextInputEditText.text.toString().toDouble()
            return true
        } catch (e: Exception) {
            return false
        }
    }

    fun setOnclickCancelBtn() {
        binding.cancelPriceListEditDialogBtn.setOnClickListener {
            dialog?.cancel()
        }
    }

    fun setStartActivityForResult() {
        onResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    val data: Intent = it.data!!
                    try {
                        if (Build.VERSION.SDK_INT < 28) {
                            val bitmap = MediaStore.Images.Media.getBitmap(
                                requireActivity().contentResolver, data.data
                            )
                            binding.imageProductOfShopEdit.setImageBitmap(bitmap)
                        } else {
                            val source = ImageDecoder.createSource(
                                requireActivity().contentResolver,
                                data.data!!
                            )
                            val bitmap = ImageDecoder.decodeBitmap(source)
                            binding.imageProductOfShopEdit.setImageBitmap(bitmap)
                        }
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                    }
                }
            }
    }

    fun setOnRequestPermisson() {
        onRequestLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                onResultLauncher.launch(intent)
            }
        }
    }

    fun setOnClickChooseImageBtn() {
        binding.chooseImageEditBtn.setOnClickListener {
            if (requireContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                onRequestLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            } else {
                val inten = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                onResultLauncher.launch(inten)
            }
        }
    }
}