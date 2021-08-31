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
import com.thuctaptotnghiep.doantttn.utils.Constant
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.adapter.DetailShopProductByProductAdapter
import com.thuctaptotnghiep.doantttn.api.response.DetailShopProductFullInformation
import com.thuctaptotnghiep.doantttn.databinding.FragmentFindProductBinding
import com.thuctaptotnghiep.doantttn.dialog.LoadingDialog
import com.thuctaptotnghiep.doantttn.ui.MainScreenGuest.MainGuestActivity
import com.thuctaptotnghiep.doantttn.ui.MainScreenGuest.MainGuestViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FindProductFragment : Fragment() {

    lateinit var binding: FragmentFindProductBinding
    lateinit var viewModel: MainGuestViewModel
    lateinit var detailShopProductAdapter: DetailShopProductByProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_find_product,
            container,
            false
        )
        viewModel = (activity as MainGuestActivity).viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        intialViewModel()
        setOnClickSearchBtn()
    }

    override fun onStop() {
        super.onStop()
        viewModel.clearListPriceListByProduct()
    }

    fun intialViewModel() {
        viewModel.listListDetailShopProductByProduct.observe(viewLifecycleOwner, {
            detailShopProductAdapter.diff.submitList(it)
        })
    }

    fun setUpAdapter() {
        detailShopProductAdapter = DetailShopProductByProductAdapter()
        binding.listProductRecycleViewSearchResult.layoutManager = LinearLayoutManager(context)
        binding.listProductRecycleViewSearchResult.adapter = detailShopProductAdapter
        detailShopProductAdapter.setDetailShopProductByProductItemOnClickListener {
            setOnClickItemResultFound(it)
        }
    }

    fun setOnClickSearchBtn() {
        binding.nameProductSearchTextInputLayout.setEndIconOnClickListener {
            CoroutineScope(Dispatchers.Default).launch {
                val prefs = requireActivity().getSharedPreferences(
                    Constant.SHARE_PREFERENCE_NAME,
                    Context.MODE_PRIVATE
                )
                val token = prefs.getString("token", "null")
                if (token == "null") {
                    Snackbar.make(binding.root, "Token null", Snackbar.LENGTH_LONG)
                        .setBackgroundTint(
                            Color.RED
                        ).show()
                } else {
                    val loadingDialog = LoadingDialog()
                    loadingDialog.show(requireActivity().supportFragmentManager,"loading dialog")
                    viewModel.getListPriceListByNameProduct(
                        token!!,
                        binding.nameProductSearchEditText.text.toString()
                    )
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
    }

    fun setOnClickItemResultFound(detailShopProductFullInformation: DetailShopProductFullInformation) {
        val goToProductInformationFrag =
            FindProductFragmentDirections.actionFindProductFragmentToInformationProductFragment(
                detailShopProductFullInformation
            )
        findNavController().navigate(goToProductInformationFrag)
    }
}