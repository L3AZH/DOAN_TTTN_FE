package com.thuctaptotnghiep.doantttn.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.adapter.CartConfirmAdapter
import com.thuctaptotnghiep.doantttn.databinding.ConfirmPayDialogBinding
import com.thuctaptotnghiep.doantttn.ui.MainScreenGuest.MainGuestActivity
import com.thuctaptotnghiep.doantttn.ui.MainScreenGuest.MainGuestViewModel

class ConfirmPayDialog(val lifecycleOwner: LifecycleOwner) : DialogFragment() {

    lateinit var binding: ConfirmPayDialogBinding
    lateinit var cartConfirmAdapter: CartConfirmAdapter
    lateinit var viewModel: MainGuestViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val layoutInflater = requireActivity().layoutInflater
            binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.confirm_pay_dialog, null, false)
            viewModel = (requireActivity() as MainGuestActivity).viewModel

            setUpAdapter()
            intialViewModel()

            builder.setView(binding.root)
            builder.create()

        } ?: throw  IllegalStateException("Activity must not be empty !!")
    }

    fun setUpAdapter(){
        cartConfirmAdapter = CartConfirmAdapter()
        binding.listCartConfirmRecycleView.layoutManager = LinearLayoutManager(requireContext())
        binding.listCartConfirmRecycleView.adapter = cartConfirmAdapter
    }

    fun intialViewModel(){
        viewModel.listCart.observe(lifecycleOwner,{
            cartConfirmAdapter.diff.submitList(it)
            var total:Double = 0.0
            for(item in it){
                total += item.price*item.amount
            }
            binding.totalListCartTextView.text = "Total: ${total} $"
        })
    }
}