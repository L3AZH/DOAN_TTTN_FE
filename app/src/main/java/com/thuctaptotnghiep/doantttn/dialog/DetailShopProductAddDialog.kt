package com.thuctaptotnghiep.doantttn.dialog

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
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
import com.thuctaptotnghiep.doantttn.api.response.Product
import com.thuctaptotnghiep.doantttn.api.response.Shop
import com.thuctaptotnghiep.doantttn.databinding.DetailShopProductAddDialogBinding
import com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.MainAdminActivity
import com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.MainAdminViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalStateException

class DetailShopProductAddDialog : DialogFragment() {

    lateinit var binding: DetailShopProductAddDialogBinding
    lateinit var viewModel: MainAdminViewModel
    lateinit var shopArrayAdapter: ShopArrayAdapter
    lateinit var productArrayAdapter: ProductArrayAdapter
    lateinit var selectedShop: Shop
    lateinit var selectedProduct: Product

    /**
     * Request and OnResult launcher
     */
    lateinit var onResultLauncher: ActivityResultLauncher<Intent>
    lateinit var onRequestLauncher: ActivityResultLauncher<String>

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            binding = DataBindingUtil.inflate(inflater, R.layout.detail_shop_product_add_dialog, null, false)
            viewModel = (requireActivity() as MainAdminActivity).viewModel

            setUpShopArrayAdapter()
            setUpProductArrayAdapter()
            /**
             * Request and OnResult launcher
             */
            setStartActivityForResult()
            setOnRequestPermission()

            setOnClickChooseImageBtn()
            setOnclickSaveBtn()

            builder.setView(binding.root)
            builder.create()
        } ?: throw IllegalStateException("Activity must not empty")
    }

    fun setStartActivityForResult() {
        onResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    val data: Intent = it.data!!
                    try {
                        if (Build.VERSION.SDK_INT < 28) {
                            val bitmap = MediaStore.Images.Media.getBitmap(
                                requireActivity().contentResolver,
                                data.data
                            )
                            binding.imageProductOfShop.setImageBitmap(bitmap)
                        } else {
                            val source = ImageDecoder.createSource(
                                requireActivity().contentResolver,
                                data.data!!
                            )
                            val bitmap = ImageDecoder.decodeBitmap(source)
                            binding.imageProductOfShop.setImageBitmap(bitmap)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
    }

    fun setOnRequestPermission() {
        onRequestLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                onResultLauncher.launch(intent)
            }
        }
    }

    fun setUpShopArrayAdapter() {
        shopArrayAdapter = ShopArrayAdapter(requireContext(), viewModel.listShop.value!!)
        (binding.shopSpinnervalue as AutoCompleteTextView).setAdapter(shopArrayAdapter)
        binding.shopSpinnervalue.setText(shopArrayAdapter.getItem(0).toString(), false)
        (binding.shopSpinnervalue as AutoCompleteTextView).onItemClickListener =
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
        selectedShop = shopArrayAdapter.getItem(0)!!
    }

    fun setUpProductArrayAdapter() {
        productArrayAdapter = ProductArrayAdapter(requireContext(), viewModel.listProduct.value!!)
        (binding.productSpinnerValue as AutoCompleteTextView).setAdapter(productArrayAdapter)
        binding.productSpinnerValue.setText(productArrayAdapter.getItem(0).toString(), false)
        (binding.productSpinnerValue as AutoCompleteTextView).onItemClickListener =
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
        selectedProduct = productArrayAdapter.getItem(0)!!
    }

    fun setOnClickChooseImageBtn() {
        binding.chooseImageBtn.setOnClickListener {
            if (requireContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
                onRequestLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            } else {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                onResultLauncher.launch(intent)
            }
        }
    }

    fun setOnclickSaveBtn() {
        binding.savePriceListBtn.setOnClickListener {
            val prefs = requireActivity().getSharedPreferences(
                Constant.SHARE_PREFERENCE_NAME,
                Context.MODE_PRIVATE
            )
            val token = prefs.getString("token", "null")
            if (binding.imageProductOfShop.drawable == null) {
                val dialoginform = InformDialog("fail", "Please choose image before input")
                dialoginform.show(requireActivity().supportFragmentManager, "dialog inform")
            } else if (!checkPricevalid()) {
                val dialoginform = InformDialog("fail", "Please enter price")
                dialoginform.show(requireActivity().supportFragmentManager, "dialog inform")
            } else {
                CoroutineScope(Dispatchers.Default).launch {
                    val loadingDialog = LoadingDialog()
                    loadingDialog.show(requireActivity().supportFragmentManager,"loading dialog")
                    val result = viewModel.createNewDetailShopProductObject(
                        token!!,
                        selectedShop.idShop,
                        selectedProduct.idProduct,
                        binding.priceTextInputEditText.text.toString().toDouble(),
                        (binding.imageProductOfShop.drawable as BitmapDrawable).bitmap
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
            binding.priceTextInputEditText.text.toString().toDouble()
            return true
        } catch (e: Exception) {
            return false
        }
    }

}