package com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.fragment

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
import com.thuctaptotnghiep.doantttn.utils.Constant
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.adapter.BillDetailAdapter
import com.thuctaptotnghiep.doantttn.databinding.FragmentDetailBillAdminBinding
import com.thuctaptotnghiep.doantttn.dialog.LoadingDialog
import com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.MainAdminActivity
import com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.MainAdminViewModel
import com.thuctaptotnghiep.doantttn.ui.MainScreenGuest.fragment.DetailBillFragmentArgs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class DetailBillAdminFragment : Fragment() {


    lateinit var binding: FragmentDetailBillAdminBinding
    lateinit var viewModel: MainAdminViewModel
    lateinit var billDetailAdapter: BillDetailAdapter
    val args: DetailBillFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_detail_bill_admin,
            container,
            false
        )
        viewModel = (activity as MainAdminActivity).viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        intialViewModel()
    }

    override fun onStop() {
        super.onStop()
        viewModel.clearListDetailBill()
    }

    fun intialViewModel() {
        viewModel.listDetailBill.observe(viewLifecycleOwner, {
            billDetailAdapter.diff.submitList(it)
        })
        CoroutineScope(Dispatchers.Default).launch {
            val prefs = requireActivity().getSharedPreferences(
                Constant.SHARE_PREFERENCE_NAME,
                Context.MODE_PRIVATE
            )
            val token = prefs.getString("token", "null")
            if (token == "null") {
                Snackbar.make(binding.root, "Token null", Snackbar.LENGTH_LONG).setBackgroundTint(
                    Color.RED
                ).show()
            } else {
                val loadingDialog = LoadingDialog()
                loadingDialog.show(requireActivity().supportFragmentManager,"loading dialog")
                viewModel.getAllListDetailBill(token!!, args.bill.idBill)
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

    fun setUpAdapter() {
        billDetailAdapter = BillDetailAdapter()
        binding.listBillDetailRecycleViewAdmin.layoutManager = LinearLayoutManager(context)
        binding.listBillDetailRecycleViewAdmin.adapter = billDetailAdapter
    }
}