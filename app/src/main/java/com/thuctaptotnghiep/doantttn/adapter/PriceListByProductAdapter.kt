package com.thuctaptotnghiep.doantttn.adapter

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.api.response.PriceListFullInformation
import com.thuctaptotnghiep.doantttn.databinding.ItemListPriceListByProductRecycleViewGuestBinding

class PriceListByProductAdapter :
    RecyclerView.Adapter<PriceListByProductAdapter.PriceListByProductViewHolder>() {


    var itemOnClickListener: ((PriceListFullInformation) -> Unit)? = null

    fun setPriceListByProductItemOnClickListener(listener: ((PriceListFullInformation) -> Unit)) {
        this.itemOnClickListener = listener
    }

    var diffCalllBack = object : DiffUtil.ItemCallback<PriceListFullInformation>() {
        override fun areItemsTheSame(
            oldItem: PriceListFullInformation,
            newItem: PriceListFullInformation
        ): Boolean {
            return (oldItem.idProduct == newItem.idProduct && oldItem.idShop == newItem.idShop)
        }

        override fun areContentsTheSame(
            oldItem: PriceListFullInformation,
            newItem: PriceListFullInformation
        ): Boolean {
            return oldItem == newItem
        }
    }

    var diff = AsyncListDiffer(this, diffCalllBack)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PriceListByProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemListPriceListByProductRecycleViewGuestBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.item_list_price_list_by_product_recycle_view_guest, parent, false
        )
        return PriceListByProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PriceListByProductViewHolder, position: Int) {
        holder.setUpBinding(diff.currentList[position], itemOnClickListener!!)
    }

    override fun getItemCount(): Int {
        if (diff.currentList == null || diff.currentList.isEmpty()) return 0
        return diff.currentList.size
    }

    inner class PriceListByProductViewHolder(val binding: ItemListPriceListByProductRecycleViewGuestBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setUpBinding(
            priceListFullInformation: PriceListFullInformation,
            listener: (PriceListFullInformation) -> Unit
        ) {
            binding.nameProductTextView.text =
                "Name Product: " + priceListFullInformation.nameProduct
            binding.nameShopTextView.text = "Shop: " + priceListFullInformation.nameShop
            binding.priceProductTextView.text =
                "Price: " + priceListFullInformation.price.toString() + "$"
            binding.imagePriceList.setImageBitmap(
                BitmapFactory.decodeByteArray(
                    priceListFullInformation.image.data,
                    0,
                    priceListFullInformation.image.data.size
                )
            )
            itemView.setOnClickListener {
                listener.let {
                    it(priceListFullInformation)
                }
            }
        }

    }
}