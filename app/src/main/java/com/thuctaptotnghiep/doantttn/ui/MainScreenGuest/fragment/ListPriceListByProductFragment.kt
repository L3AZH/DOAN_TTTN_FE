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
import com.thuctaptotnghiep.doantttn.adapter.PriceListByProductAdapter
import com.thuctaptotnghiep.doantttn.api.response.PriceListFullInformation
import com.thuctaptotnghiep.doantttn.databinding.FragmentListPriceListByProductBinding
import com.thuctaptotnghiep.doantttn.ui.MainScreenGuest.MainGuestActivity
import com.thuctaptotnghiep.doantttn.ui.MainScreenGuest.MainGuestViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ListPriceListByProductFragment : Fragment() {

    lateinit var binding: FragmentListPriceListByProductBinding
    lateinit var viewModel: MainGuestViewModel
    lateinit var priceListByProductAdapter: PriceListByProductAdapter

    val args: ListPriceListByProductFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_list_price_list_by_product,
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
        viewModel.listListPriceListByProduct.observe(viewLifecycleOwner, {
            priceListByProductAdapter.diff.submitList(it)
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
                viewModel.getListPriceListByProduct(token, args.idProduct)
            }
        }
    }

    fun setUpPriceListByProductAdapter() {
        priceListByProductAdapter = PriceListByProductAdapter()
        binding.listPriceListByProductRecycleView.layoutManager = LinearLayoutManager(context)
        binding.listPriceListByProductRecycleView.adapter = priceListByProductAdapter
        priceListByProductAdapter.setPriceListByProductItemOnClickListener {
            setOnclickPriceListByProductItem(it)
        }
    }

    fun setOnclickPriceListByProductItem(priceListFullInformation: PriceListFullInformation){
        val goToInformationProductFragment =
            ListPriceListByProductFragmentDirections.actionListPriceListByProductFragmentToInformationProductFragment(
                priceListFullInformation
            )
        findNavController().navigate(goToInformationProductFragment)
    }
}