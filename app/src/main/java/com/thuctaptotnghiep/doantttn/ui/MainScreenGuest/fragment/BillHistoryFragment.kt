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
import com.thuctaptotnghiep.doantttn.adapter.BillAdapter
import com.thuctaptotnghiep.doantttn.api.response.Bill
import com.thuctaptotnghiep.doantttn.databinding.FragmentBillHistoryBinding
import com.thuctaptotnghiep.doantttn.ui.MainScreenGuest.MainGuestActivity
import com.thuctaptotnghiep.doantttn.ui.MainScreenGuest.MainGuestViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class BillHistoryFragment : Fragment() {

    lateinit var binding: FragmentBillHistoryBinding
    lateinit var viewModel: MainGuestViewModel
    lateinit var billAdapter: BillAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_bill_history,
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
    }

    fun intialViewModel() {
        viewModel.listBill.observe(viewLifecycleOwner, {
            billAdapter.diff.submitList(it)
        })
        CoroutineScope(Dispatchers.Default).launch {
            val prefs = requireActivity().getSharedPreferences(
                Constant.SHARE_PREFERENCE_NAME,
                Context.MODE_PRIVATE
            )
            val token = prefs.getString("token", "null")
            val idAccount = prefs.getString("idAccount", "null")
            if (token == "null" || idAccount == "null") {
                Snackbar.make(binding.root, "Token or IdAccount null", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(
                        Color.RED
                    ).show()
            } else {
                viewModel.getBillByIdAccount(token!!, idAccount!!)
            }
        }
    }

    fun setUpAdapter() {
        billAdapter = BillAdapter()
        binding.listBillRecycleView.layoutManager = LinearLayoutManager(context)
        binding.listBillRecycleView.adapter = billAdapter
        billAdapter.setOnItemBillOnClickCallBack {
            setOnItemBillClick(it)
        }
    }

    fun setOnItemBillClick(bill:Bill){
        val goToDetailBill = BillHistoryFragmentDirections.actionBillHistoryFragmentToDetailBillFragment(bill)
        findNavController().navigate(goToDetailBill)
    }
}