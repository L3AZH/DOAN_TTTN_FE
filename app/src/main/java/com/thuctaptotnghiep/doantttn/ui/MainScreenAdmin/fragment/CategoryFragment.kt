package com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.thuctaptotnghiep.doantttn.Constant
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.adapter.CategoryAdapter
import com.thuctaptotnghiep.doantttn.api.response.Category
import com.thuctaptotnghiep.doantttn.databinding.FragmentCategoryBinding
import com.thuctaptotnghiep.doantttn.dialog.CategoryAddDialog
import com.thuctaptotnghiep.doantttn.dialog.CategoryEditDialog
import com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.MainAdminActivity
import com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.MainAdminViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryFragment : Fragment() {


    lateinit var binding: FragmentCategoryBinding
    lateinit var viewModel: MainAdminViewModel
    lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category, container, false)
        viewModel = (activity as MainAdminActivity).viewModel
        /**
         * Ta can get list shop va product de khi nguoi dung vao muc price list tu dau se khong
         * bi crash chuong trinh
         */
        initListShopAndProduct()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycleView()
        intialViewModel()
        setOnClickAddBtn()
    }

    fun intialViewModel() {
        viewModel.listCategory.observe(viewLifecycleOwner, Observer {
            categoryAdapter.diff.submitList(it)
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
                viewModel.getAllCategory(token)
            }
        }
    }

    fun setUpRecycleView() {
        categoryAdapter = CategoryAdapter()
        categoryAdapter.setItemCategoryAdapterOnClickListener {
            itemCategoryClickListener(it)
        }
        binding.listCategoryRecycleView.layoutManager = LinearLayoutManager(activity)
        binding.listCategoryRecycleView.adapter = categoryAdapter
    }

    fun itemCategoryClickListener(category:Category){
        val dialogEdit = CategoryEditDialog(category)
        dialogEdit.show(requireActivity().supportFragmentManager,"editing category dialog")
    }

    fun setOnClickAddBtn(){
        binding.addCategoryFloatingBtn.setOnClickListener {
            val addDialog = CategoryAddDialog()
            addDialog.show(requireActivity().supportFragmentManager,"adding category dialog")
        }
    }

    fun initListShopAndProduct() = CoroutineScope(Dispatchers.Default).launch {
        val pref = requireActivity().getSharedPreferences(Constant.SHARE_PREFERENCE_NAME,Context.MODE_PRIVATE)
        val token = pref.getString("token","null")!!
        viewModel.getAllShop(token)
        viewModel.getAllProduct(token)
    }
}