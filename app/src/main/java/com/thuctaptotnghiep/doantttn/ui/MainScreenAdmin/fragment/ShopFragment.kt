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
import com.thuctaptotnghiep.doantttn.adapter.ShopAdapter
import com.thuctaptotnghiep.doantttn.api.response.Shop
import com.thuctaptotnghiep.doantttn.databinding.FragmentShopBinding
import com.thuctaptotnghiep.doantttn.dialog.ShopAddDialog
import com.thuctaptotnghiep.doantttn.dialog.ShopEditDialog
import com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.MainAdminActivity
import com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.MainAdminViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShopFragment : Fragment() {

    lateinit var binding:FragmentShopBinding
    lateinit var viewModel:MainAdminViewModel
    lateinit var shopAdapter: ShopAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_shop,container,false)
        viewModel = (activity as MainAdminActivity).viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycleView()
        initViewModel()
        setOnClickAddShopFloatingBtn()
    }

    fun initViewModel(){
        viewModel.listShop.observe(viewLifecycleOwner,{
            shopAdapter.diff.submitList(it)
        })
        val prefs = requireActivity().getSharedPreferences(Constant.SHARE_PREFERENCE_NAME,Context.MODE_PRIVATE)
        val token = prefs.getString("token","null")!!
        CoroutineScope(Dispatchers.Default).launch {
            if (token == "null") {
                Snackbar.make(binding.root, "token null", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(Color.RED).show()
            }else{
                viewModel.getAllShop(token)
            }
        }
    }

    fun setUpRecycleView(){
        shopAdapter = ShopAdapter()
        binding.listShopRecycleView.layoutManager = LinearLayoutManager(activity)
        binding.listShopRecycleView.adapter = shopAdapter
        shopAdapter.setItemShopOnClickListener {
            setShopItemClickListener(it)
        }
    }

    fun setShopItemClickListener(shop:Shop){
        val dialogEditShop = ShopEditDialog(shop)
        dialogEditShop.show(requireActivity().supportFragmentManager,"dialog edit shop")
    }

    fun setOnClickAddShopFloatingBtn(){
        binding.addShopFloatingBtn.setOnClickListener {
            val dialogAddShop = ShopAddDialog()
            dialogAddShop.show(requireActivity().supportFragmentManager,"dialog add shop")
        }
    }
}