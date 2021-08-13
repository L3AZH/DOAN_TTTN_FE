package com.thuctaptotnghiep.doantttn.adapter

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.api.response.BillDetail
import com.thuctaptotnghiep.doantttn.databinding.ItemDetailBillRecycleViewGuestBinding

class BillDetailAdapter : RecyclerView.Adapter<BillDetailAdapter.BillDetailViewHolder>() {

    val diffCallBack = object : DiffUtil.ItemCallback<BillDetail>() {
        override fun areItemsTheSame(oldItem: BillDetail, newItem: BillDetail): Boolean {
            return (oldItem.idBill == newItem.idBill &&
                    oldItem.idProduct == newItem.idProduct &&
                    oldItem.idShop == newItem.idShop)
        }

        override fun areContentsTheSame(oldItem: BillDetail, newItem: BillDetail): Boolean {
            return oldItem == newItem
        }
    }

    val diff = AsyncListDiffer(this, diffCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillDetailViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding:ItemDetailBillRecycleViewGuestBinding = DataBindingUtil.inflate(layoutInflater,
            R.layout.item_detail_bill_recycle_view_guest,parent,false)
        return BillDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BillDetailViewHolder, position: Int) {
        holder.setUpBinding(diff.currentList[position])
    }

    override fun getItemCount(): Int {
        if(diff.currentList.isEmpty() || diff.currentList == null) return 0
        return diff.currentList.size
    }

    inner class BillDetailViewHolder(val binding: ItemDetailBillRecycleViewGuestBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setUpBinding(billDetail: BillDetail) {
            binding.nameProductBillDetailItem.text = "Name: " + billDetail.nameProduct
            binding.nameShopBillDetailItem.text = "Name Shop: " + billDetail.nameShop
            binding.amountBillDetailItem.text = "Amount: " + billDetail.amount.toString()
            binding.priceProductConfirmCartItem.text = "Price: " + billDetail.priceDetail.toString()
            binding.imageCartConfirItem.setImageBitmap(
                BitmapFactory.decodeByteArray(
                    billDetail.image.data,
                    0,
                    billDetail.image.data.size
                )
            )
        }
    }
}