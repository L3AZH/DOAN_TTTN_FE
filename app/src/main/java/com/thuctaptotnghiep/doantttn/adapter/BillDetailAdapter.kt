package com.thuctaptotnghiep.doantttn.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.os.Build
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.api.response.BillDetail
import com.thuctaptotnghiep.doantttn.databinding.ItemDetailBillRecycleViewGuestBinding
import com.thuctaptotnghiep.doantttn.utils.CurrencyConvert

class BillDetailAdapter(val context: Context) :
    RecyclerView.Adapter<BillDetailAdapter.BillDetailViewHolder>() {

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
        val binding: ItemDetailBillRecycleViewGuestBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.item_detail_bill_recycle_view_guest, parent, false
        )
        return BillDetailViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onBindViewHolder(holder: BillDetailViewHolder, position: Int) {
        holder.setUpBinding(diff.currentList[position], context)
    }

    override fun getItemCount(): Int {
        if (diff.currentList.isEmpty()) return 0
        return diff.currentList.size
    }

    inner class BillDetailViewHolder(val binding: ItemDetailBillRecycleViewGuestBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.R)
        fun setUpBinding(billDetail: BillDetail, context: Context) {

            val displayMetrics = DisplayMetrics()
            context.display!!.getRealMetrics(displayMetrics)
            val mPaint = Paint()
            mPaint.textSize = 16f

            val priceText = CurrencyConvert.convertCurrencyToString(billDetail.priceOrigin)

            val sizeTextNameProduct =
                mPaint.measureText(billDetail.nameProduct, 0, billDetail.nameProduct.length)
            val sizeTextNameShop =
                mPaint.measureText(billDetail.nameShop, 0, billDetail.nameShop.length)
            val sizeTextPrice = mPaint.measureText(priceText, 0, priceText.length)
            val sizeTextAmount = mPaint.measureText(
                "Amount: "+billDetail.amount.toString(),
                0,
                billDetail.amount.toString().length + "Amount: ".length
            )

            val listSize: MutableList<Float> = mutableListOf()
            listSize.add(sizeTextNameProduct)
            listSize.add(sizeTextNameShop)
            listSize.add(sizeTextPrice)

            val sizeMax: Float = (listSize.maxOrNull() ?: 0) as Float


            if (sizeMax > (displayMetrics.widthPixels - 80 - 10 - 10 - sizeTextAmount - 64) / 3) {
                val maxWidth =
                    (displayMetrics.widthPixels - 80 - 10 - 64 - 10 - sizeTextAmount - 64) / 3
                binding.nameProductBillDetailItem.maxWidth = maxWidth.toInt()
                binding.nameShopBillDetailItem.maxWidth = maxWidth.toInt()
                binding.priceProductConfirmCartItem.maxWidth = maxWidth.toInt()
            }

            binding.nameProductBillDetailItem.text = "Name: " + billDetail.nameProduct
            binding.nameShopBillDetailItem.text = "Shop: " + billDetail.nameShop
            binding.amountBillDetailItem.text = "Amount: " + billDetail.amount.toString()
            binding.priceProductConfirmCartItem.text = "Price: " + priceText
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