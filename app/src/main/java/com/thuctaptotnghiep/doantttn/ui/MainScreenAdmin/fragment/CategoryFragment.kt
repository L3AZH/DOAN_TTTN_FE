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
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.adapter.CategoryAdapter
import com.thuctaptotnghiep.doantttn.databinding.FragmentCategoryBinding
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycleView()
        intialViewModel()
    }

    fun intialViewModel() {
        viewModel.listCategory.observe(viewLifecycleOwner, Observer {
            categoryAdapter.diff.submitList(it)
        })
        CoroutineScope(Dispatchers.Default).launch {
            val pref = requireActivity().getSharedPreferences(
                "com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.fragment",
                Context.MODE_PRIVATE
            )
            val token: String = pref.getString("token", "null")!!
            if (token == "null") {
                Snackbar.make(binding.root, "token null", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(
                        Color.RED
                    ).show()
            } else {
                viewModel.getAllCategory(token)
            }
        }
    }

    fun setUpRecycleView() {
        categoryAdapter = CategoryAdapter()
        binding.listCategoryRecycleView.layoutManager = LinearLayoutManager(activity)
        binding.listCategoryRecycleView.adapter = categoryAdapter
    }
}