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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.thuctaptotnghiep.doantttn.Constant
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.adapter.DetailShopProductByProductAdapter
import com.thuctaptotnghiep.doantttn.api.response.DetailShopProductFullInformation
import com.thuctaptotnghiep.doantttn.databinding.FragmentListDetailShopProductByProductBinding
import com.thuctaptotnghiep.doantttn.dialog.LoadingDialog
import com.thuctaptotnghiep.doantttn.ui.MainScreenGuest.MainGuestActivity
import com.thuctaptotnghiep.doantttn.ui.MainScreenGuest.MainGuestViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ListDetailShopProductByProductFragment : Fragment() {

    lateinit var binding: FragmentListDetailShopProductByProductBinding
    lateinit var viewModel: MainGuestViewModel
    lateinit var detailShopProductByProductAdapter: DetailShopProductByProductAdapter

    val args: ListDetailShopProductByProductFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_list_detail_shop_product_by_product,
            container,
            false
        )
        viewModel = (activity as MainGuestActivity).viewModel
        return binding.root
    }

    override fun onStop() {
        super.onStop()
        viewModel.clearListPriceListByProduct()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpPriceListByProductAdapter()
        intialViewModel()
    }

    fun intialViewModel() {
        viewModel.listListDetailShopProductByProduct.observe(viewLifecycleOwner, {
            detailShopProductByProductAdapter.diff.submitList(it)
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
                val loadingDialog = LoadingDialog()
                loadingDialog.show(requireActivity().supportFragmentManager,"loading dialog")
                viewModel.getListPriceListByProduct(token, args.idProduct)
                if(loadingDialog.dialog == null){
                    delay(1000)
                    loadingDialog.cancelLoading()
                }
                else{
                    loadingDialog.cancelLoading()
                }
            }
        }
    }

    fun setUpPriceListByProductAdapter() {
        detailShopProductByProductAdapter = DetailShopProductByProductAdapter()
        binding.listPriceListByProductRecycleView.layoutManager = LinearLayoutManager(context)
        binding.listPriceListByProductRecycleView.adapter = detailShopProductByProductAdapter
        detailShopProductByProductAdapter.setDetailShopProductByProductItemOnClickListener {
            setOnclickDetailShopProductByProductItem(it)
        }
    }

    fun setOnclickDetailShopProductByProductItem(detailShopProductFullInformation: DetailShopProductFullInformation){
        val goToInformationProductFragment =
            ListDetailShopProductByProductFragmentDirections.actionListDetailShopProductByProductFragmentToInformationProductFragment(
                detailShopProductFullInformation
            )
        findNavController().navigate(goToInformationProductFragment)
    }
}