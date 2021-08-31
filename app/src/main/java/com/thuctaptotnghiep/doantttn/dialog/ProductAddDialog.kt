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
import com.thuctaptotnghiep.doantttn.databinding.ProductAddDialogBinding
import com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.MainAdminActivity
import com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.MainAdminViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalStateException

class ProductAddDialog(val categoryList: List<Category>) : DialogFragment() {

    lateinit var binding: ProductAddDialogBinding
    lateinit var viewModel: MainAdminViewModel
    lateinit var categoryArrayAdapter: CategoryArrayAdapter
    lateinit var selectedCategory: Category

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            binding = DataBindingUtil.inflate(inflater, R.layout.product_add_dialog, null, false)
            viewModel = (requireActivity() as MainAdminActivity).viewModel

            setUpSpinnerTextInputAdapter()
            setOnClickSaveProductBtn()

            builder.setView(binding.root)
            builder.create()
        } ?: throw IllegalStateException("Activity must not empty")
    }

    fun setUpSpinnerTextInputAdapter() {
        categoryArrayAdapter = CategoryArrayAdapter(requireContext(), categoryList)
        (binding.nameCategoryInputTextEditSpinner as AutoCompleteTextView).setAdapter(
            categoryArrayAdapter
        )
        binding.nameCategoryInputTextEditSpinner.setText(
            categoryArrayAdapter.getItem(0).toString(),
            false
        );
        (binding.nameCategoryInputTextEditSpinner as AutoCompleteTextView).onItemClickListener =
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
        selectedCategory = categoryArrayAdapter.getItem(0)!!
    }

    fun setOnClickSaveProductBtn() {
        binding.addProductBtn.setOnClickListener {
            val prefs = requireActivity().getSharedPreferences(
                Constant.SHARE_PREFERENCE_NAME,
                Context.MODE_PRIVATE
            )
            val token = prefs.getString("token", "null")
            CoroutineScope(Dispatchers.Default).launch {
                val result = viewModel.createNewProduct(
                    token!!,
                    binding.nameProductInputTextEdit.text.toString(),
                    selectedCategory.idCategory
                ).await()
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