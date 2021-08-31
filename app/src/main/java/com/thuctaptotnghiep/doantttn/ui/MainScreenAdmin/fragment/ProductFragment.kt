package com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.thuctaptotnghiep.doantttn.utils.Constant
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.adapter.ProductAdapter
import com.thuctaptotnghiep.doantttn.api.response.Product
import com.thuctaptotnghiep.doantttn.databinding.FragmentProductBinding
import com.thuctaptotnghiep.doantttn.dialog.InformDialog
import com.thuctaptotnghiep.doantttn.dialog.ProductAddDialog
import com.thuctaptotnghiep.doantttn.dialog.ProductEditDialog
import com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.MainAdminActivity
import com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.MainAdminViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductFragment : Fragment() {

    lateinit var binding: FragmentProductBinding
    lateinit var viewModel: MainAdminViewModel
    lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product, container, false)
        viewModel = (activity as MainAdminActivity).viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycleView()
        initViewModel()
        setOnclickAddProductFloatingBtn()
    }

    fun initViewModel() {
        viewModel.listProduct.observe(viewLifecycleOwner, {
            productAdapter.diff.submitList(it)
        })
        CoroutineScope(Dispatchers.Default).launch {
            val pref = requireActivity().getSharedPreferences(
                Constant.SHARE_PREFERENCE_NAME,
                Context.MODE_PRIVATE
            )
            val token = pref.getString("token", "null")!!
            if (token == "null") {
                Snackbar.make(binding.root, "token null", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(Color.RED).show()
            } else {
                viewModel.getAllProduct(token)
            }
        }
    }

    fun setUpRecycleView() {
        productAdapter = ProductAdapter()
        binding.listProductRecycleView.layoutManager = LinearLayoutManager(activity)
        binding.listProductRecycleView.adapter = productAdapter
        productAdapter.setItemProductAdapterOnClickListener {
            setOnItemProductApdapterListener(it)
        }
    }

    fun setOnItemProductApdapterListener(product: Product) {
        val dialogEditProduct = ProductEditDialog(product, viewModel.listCategory.value!!)
        dialogEditProduct.show(requireActivity().supportFragmentManager, "edit product dialog")
    }

    fun setOnclickAddProductFloatingBtn() {
        binding.addProductFloatingBtn.setOnClickListener {
            if (viewModel.listCategory.value.isNullOrEmpty()) {
                val informDialog =
                    InformDialog("fail", "List Category Empty !!, Can't create new Product")
                informDialog.show(requireActivity().supportFragmentManager, "error dialog")
            } else {
                val dialogAddProduct = ProductAddDialog(viewModel.listCategory.value!!)
                dialogAddProduct.show(
                    requireActivity().supportFragmentManager,
                    "add product dialog"
                )
            }

        }
    }
}