package com.thuctaptotnghiep.doantttn.ui.MainScreenGuest.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.thuctaptotnghiep.doantttn.Constant
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.adapter.BillDetailAdapter
import com.thuctaptotnghiep.doantttn.databinding.FragmentDetailBillBinding
import com.thuctaptotnghiep.doantttn.ui.MainScreenGuest.MainGuestActivity
import com.thuctaptotnghiep.doantttn.ui.MainScreenGuest.MainGuestViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DetailBillFragment : Fragment() {

    lateinit var binding:FragmentDetailBillBinding
    lateinit var billDetailAdapter: BillDetailAdapter
    lateinit var viewModel:MainGuestViewModel
    val args:DetailBillFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_detail_bill,container,false)
        viewModel = (activity as MainGuestActivity).viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        initViewModel()
    }

    fun initViewModel(){
        viewModel.listBillDetail.observe(viewLifecycleOwner,{
            billDetailAdapter.diff.submitList(it)
        })
        CoroutineScope(Dispatchers.Default).launch {
            val prefs = requireActivity().getSharedPreferences(Constant.SHARE_PREFERENCE_NAME,Context.MODE_PRIVATE)
            val token = prefs.getString("token","null")
            if(token == "null"){
                Snackbar.make(binding.root,"Token null",Snackbar.LENGTH_LONG).setBackgroundTint(
                    Color.RED).show()
            }
            else{
                viewModel.getBillDetailByIdBill(token!!,args.bill.idBill)
            }
        }
    }

    fun setUpAdapter(){
        billDetailAdapter = BillDetailAdapter()
        binding.listBillDetailRecycleView.layoutManager = LinearLayoutManager(context)
        binding.listBillDetailRecycleView.adapter = billDetailAdapter
    }
}