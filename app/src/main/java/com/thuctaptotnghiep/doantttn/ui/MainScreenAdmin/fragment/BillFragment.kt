package com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.fragment

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
import com.thuctaptotnghiep.doantttn.adapter.BillAdapter
import com.thuctaptotnghiep.doantttn.api.response.Bill
import com.thuctaptotnghiep.doantttn.databinding.FragmentBillBinding
import com.thuctaptotnghiep.doantttn.dialog.ConfirmBillDialog
import com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.MainAdminActivity
import com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.MainAdminViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class BillFragment : Fragment() {

    lateinit var binding: FragmentBillBinding
    lateinit var billAdpater: BillAdapter
    lateinit var viewModel: MainAdminViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_bill, container, false)
        viewModel = (activity as MainAdminActivity).viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        intialViewModel()
    }

    fun intialViewModel() {
        viewModel.listBill.observe(viewLifecycleOwner, {
            billAdpater.diff.submitList(it)
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

                viewModel.getAllBill(token!!)
            }
        }
    }

    fun setUpAdapter() {
        billAdpater = BillAdapter()
        binding.listBillRecycleViewAdmin.layoutManager = LinearLayoutManager(context)
        binding.listBillRecycleViewAdmin.adapter = billAdpater
        billAdpater.setOnItemBillOnClickCallBack {
            setOnClickItemBill(it)
        }
        billAdpater.setOnLongClickItemBillCallBack {
            setLongClickItemBill(it)
        }
    }

    fun setOnClickItemBill(bill:Bill){
        val goToDetailBill = BillFragmentDirections.actionBillFragmentToDetailBillAdminFragment(bill)
        findNavController().navigate(goToDetailBill)
    }

    fun setLongClickItemBill(bill: Bill):Boolean{
        val confirmBillDialog = ConfirmBillDialog(bill)
        confirmBillDialog.show(requireActivity().supportFragmentManager,"confirm bill dialog ")
        return true
    }


}