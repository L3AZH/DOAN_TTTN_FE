package com.thuctaptotnghiep.doantttn.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.thuctaptotnghiep.doantttn.utils.Constant
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.adapter.CategoryArrayAdapter
import com.thuctaptotnghiep.doantttn.api.response.Category
import com.thuctaptotnghiep.doantttn.api.response.Product
import com.thuctaptotnghiep.doantttn.databinding.ProductEditDialogBinding
import com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.MainAdminActivity
import com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.MainAdminViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductEditDialog(var product: Product, val categoryList: List<Category>) : DialogFragment() {

    lateinit var binding: ProductEditDialogBinding
    lateinit var viewModel: MainAdminViewModel
    lateinit var categoryArrayAdapter: CategoryArrayAdapter
    lateinit var selectedCategory: Category

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val layoutInflater = requireActivity().layoutInflater
            binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.product_edit_dialog, null, false)
            viewModel = (requireActivity() as MainAdminActivity).viewModel

            setUpBinding()
            setUpSpinnerTextInputAdapter()
            setOnClickDeleteBtn()
            setOnClickUpdateBtn()
            setOnclickCancelBtn()

            builder.setView(binding.root)
            builder.create()
        } ?: throw IllegalStateException("Activity must not null")
    }

    fun setUpBinding() {
        binding.nameProductIntputTextEdit.setText(product.name)
        binding.idProductForEditDialogTextView.text = product.idProduct
    }

    fun setUpSpinnerTextInputAdapter() {
        categoryArrayAdapter = CategoryArrayAdapter(requireContext(), categoryList)
        (binding.categorySpinnerEditDialogInutEditText as AutoCompleteTextView).setAdapter(
            categoryArrayAdapter
        )
        binding.categorySpinnerEditDialogInutEditText.setText(
            categoryArrayAdapter.getItemId(product.CategoryIdCategory).toString(),
            false
        );
        (binding.categorySpinnerEditDialogInutEditText as AutoCompleteTextView).onItemClickListener =
            object : AdapterView.OnItemClickListener {
                override fun onItemClick(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedCategory = categoryArrayAdapter.getItem(position)!!
                }
            }
        selectedCategory = categoryArrayAdapter.getItemId(product.CategoryIdCategory)!!
    }

    fun setOnClickDeleteBtn() {
        binding.deleteProductEditDialogBtn.setOnClickListener {
            val prefs = requireActivity().getSharedPreferences(
                Constant.SHARE_PREFERENCE_NAME,
                Context.MODE_PRIVATE
            )
            val token = prefs.getString("token", "null")!!
            CoroutineScope(Dispatchers.Default).launch {
                val result = viewModel.deleteProduct(token, product.idProduct).await()
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
        binding.saveProductEditDialogBtn.setOnClickListener {
            val prefs = requireActivity().getSharedPreferences(
                Constant.SHARE_PREFERENCE_NAME,
                Context.MODE_PRIVATE
            )
            val token = prefs.getString("token", "null")!!
            CoroutineScope(Dispatchers.Default).launch {
                val result = viewModel.updateProduct(
                    token,
                    product.idProduct,
                    binding.nameProductIntputTextEdit.text.toString(),
                    selectedCategory.idCategory
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

    fun setOnclickCancelBtn(){
        binding.cancelProductEditDialogBtn.setOnClickListener {
            dialog?.cancel()
        }
    }
}