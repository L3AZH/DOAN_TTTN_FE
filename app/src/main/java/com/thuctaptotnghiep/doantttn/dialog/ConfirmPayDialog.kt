package com.thuctaptotnghiep.doantttn.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.thuctaptotnghiep.doantttn.utils.Constant
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.adapter.CartConfirmAdapter
import com.thuctaptotnghiep.doantttn.databinding.ConfirmPayDialogBinding
import com.thuctaptotnghiep.doantttn.ui.MainScreenGuest.MainGuestActivity
import com.thuctaptotnghiep.doantttn.ui.MainScreenGuest.MainGuestViewModel
import com.thuctaptotnghiep.doantttn.utils.CurrencyConvert
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ConfirmPayDialog(val lifecycleOwner: LifecycleOwner) : DialogFragment() {

    lateinit var binding: ConfirmPayDialogBinding
    lateinit var cartConfirmAdapter: CartConfirmAdapter
    lateinit var viewModel: MainGuestViewModel

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val layoutInflater = requireActivity().layoutInflater
            binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.confirm_pay_dialog, null, false)
            viewModel = (requireActivity() as MainGuestActivity).viewModel

            setUpAdapter()
            intialViewModel()
            setOnClickCancelBtn()
            setOnClickOkBtn()

            builder.setView(binding.root)
            builder.create()

        } ?: throw  IllegalStateException("Activity must not be empty !!")
    }

    fun setUpAdapter() {
        cartConfirmAdapter = CartConfirmAdapter()
        binding.listCartConfirmRecycleView.layoutManager = LinearLayoutManager(requireContext())
        binding.listCartConfirmRecycleView.adapter = cartConfirmAdapter
    }

    fun intialViewModel() {
        viewModel.listCart.observe(lifecycleOwner, {
            cartConfirmAdapter.diff.submitList(it)
            var total: Double = 0.0
            for (item in it) {
                total += item.price * item.amount
            }
            binding.totalListCartTextView.text =
                "Total: " + CurrencyConvert.convertCurrencyToString(total)
        })
    }

    fun setOnClickCancelBtn() {
        binding.cancelConfirmBtn.setOnClickListener {
            dialog?.cancel()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun setOnClickOkBtn() {
        binding.okConfirmBtn.setOnClickListener {
            if (viewModel.listCart.value == null || viewModel.listCart.value!!.isEmpty()) {
                val errorDialog =
                    InformDialog("fail", "Your Cart is empty, please choose product to checkout !!")
                errorDialog.show(requireActivity().supportFragmentManager, "error inform dialog")
            } else {
                CoroutineScope(Dispatchers.Default).launch {
                    val prefs = requireActivity().getSharedPreferences(
                        Constant.SHARE_PREFERENCE_NAME,
                        Context.MODE_PRIVATE
                    )
                    val token = prefs.getString("token", "null")
                    val idAccount = prefs.getString("idAccount", "null")
                    val email = prefs.getString("email", "null")
                    val resultMap = viewModel.createBill(token!!, idAccount!!, email!!).await()
                    if (resultMap["flag"] as Boolean) {
                        val successInform = InformDialog("success", resultMap["message"].toString())
                        successInform.show(
                            requireActivity().supportFragmentManager,
                            "success inform dialog"
                        )
                        dialog?.cancel()
                    } else {
                        val errorInform = InformDialog("fail", resultMap["message"].toString())
                        errorInform.show(
                            requireActivity().supportFragmentManager,
                            "error inform dialog"
                        )
                    }
                }
            }
        }
    }
}