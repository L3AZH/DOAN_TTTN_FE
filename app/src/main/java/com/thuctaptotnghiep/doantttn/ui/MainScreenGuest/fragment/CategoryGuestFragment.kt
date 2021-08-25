package com.thuctaptotnghiep.doantttn.ui.MainScreenGuest.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.thuctaptotnghiep.doantttn.Constant
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.adapter.CategoryGuestAdapter
import com.thuctaptotnghiep.doantttn.databinding.FragmentCategoryGuestBinding
import com.thuctaptotnghiep.doantttn.ui.MainScreenGuest.MainGuestActivity
import com.thuctaptotnghiep.doantttn.ui.MainScreenGuest.MainGuestViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryGuestFragment : Fragment() {


    lateinit var binding: FragmentCategoryGuestBinding
    lateinit var categoryGuestAdapter: CategoryGuestAdapter
    lateinit var viewModel: MainGuestViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_category_guest,
            container,
            false
        )
        viewModel = (activity as MainGuestActivity).viewModel
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpCategoryGuestAdapter()
        intialViewModel()
    }

    fun intialViewModel() {
        viewModel.listCategory.observe(viewLifecycleOwner, {
            categoryGuestAdapter.diff.submitList(it)
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

    fun setUpCategoryGuestAdapter() {
        categoryGuestAdapter =
            CategoryGuestAdapter(requireContext(), requireActivity(), viewModel, viewLifecycleOwner)
        binding.categoryRecycleViewGuest.layoutManager = LinearLayoutManager(requireActivity())
        binding.categoryRecycleViewGuest.adapter = categoryGuestAdapter
        categoryGuestAdapter.setItemlistProductByCategoryCallBack {
            val goToListPriceListByProductFragment =
                CategoryGuestFragmentDirections.actionCategoryGuestFragmentToListDetailShopProductByProductFragment(
                    it.idProduct
                )
            findNavController().navigate(goToListPriceListByProductFragment)
        }
    }
}