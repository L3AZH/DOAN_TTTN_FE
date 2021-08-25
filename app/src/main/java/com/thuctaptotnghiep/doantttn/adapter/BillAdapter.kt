package com.thuctaptotnghiep.doantttn.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.api.response.Bill
import com.thuctaptotnghiep.doantttn.databinding.ItemBillRecycleViewGuestBinding

class BillAdapter : RecyclerView.Adapter<BillAdapter.BillViewHolder>() {

    var itemBillOnClickCallBack: ((Bill) -> Unit)? = null
    var longClickItemBillCallBack: ((Bill) -> Boolean)? = null

    fun setOnItemBillOnClickCallBack(listener: ((Bill) -> Unit)) {
        this.itemBillOnClickCallBack = listener
    }

    fun setOnLongClickItemBillCallBack(listener: ((Bill) -> Boolean)?) {
        this.longClickItemBillCallBack = listener
    }

    val diffCallBack = object : DiffUtil.ItemCallback<Bill>() {
        override fun areItemsTheSame(oldItem: Bill, newItem: Bill): Boolean {
            return oldItem.idBill == newItem.idBill
        }

        override fun areContentsTheSame(oldItem: Bill, newItem: Bill): Boolean {
            return newItem == oldItem
        }
    }
    val diff = AsyncListDiffer(this, diffCallBack)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemBillRecycleViewGuestBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.item_bill_recycle_view_guest,
            parent,
            false
        )
        return BillViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BillViewHolder, position: Int) {
        holder.setUpBinding(
            diff.currentList[position],
            itemBillOnClickCallBack!!,
            longClickItemBillCallBack
        )
    }


    override fun getItemCount(): Int {
        if (diff.currentList.isNullOrEmpty()) return 0
        return diff.currentList.size
    }

    inner class BillViewHolder(val binding: ItemBillRecycleViewGuestBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setUpBinding(
            bill: Bill,
            listener: (Bill) -> Unit,
            longClickListener: ((Bill) -> Boolean)?
        ) {
            binding.idBillTextViewItem.text = "ID: " + bill.idBill
            binding.dateBillTextViewItem.text = "Time: " + bill.date.toString()
            binding.statusBillTextView.text = "Status: " + bill.status
            itemView.setOnClickListener {
                listener.let {
                    it(bill)
                }
            }
            itemView.setOnLongClickListener {
                longClickListener.let {
                    it?.invoke(bill)
                }
                return@setOnLongClickListener true
            }
        }
    }
}