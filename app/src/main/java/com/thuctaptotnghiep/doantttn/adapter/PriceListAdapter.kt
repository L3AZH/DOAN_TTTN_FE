package com.thuctaptotnghiep.doantttn.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.api.response.PriceList
import com.thuctaptotnghiep.doantttn.databinding.ItemListPriceListRecycleViewBinding

class PriceListAdapter() : RecyclerView.Adapter<PriceListAdapter.PriceListViewHolder>() {

    var itemOnClickListener: ((priceList: PriceList) -> Unit)? = null

    fun setItemPriceListAdapterOnClickListener(listener: ((priceList: PriceList) -> Unit)) {
        this.itemOnClickListener = listener
    }


    var differCallBack = object : DiffUtil.ItemCallback<PriceList>() {
        override fun areItemsTheSame(oldItem: PriceList, newItem: PriceList): Boolean {
            return (oldItem.productIdProduct == newItem.productIdProduct && oldItem.shopIdShop == newItem.shopIdShop)
        }

        override fun areContentsTheSame(oldItem: PriceList, newItem: PriceList): Boolean {
            return oldItem == newItem
        }
    }

    val diff = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemListPriceListRecycleViewBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.item_list_price_list_recycle_view,
            parent,
            false
        )
        return PriceListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PriceListViewHolder, position: Int) {
        holder.setUpBinding(diff.currentList[position], itemOnClickListener!!)
    }

    override fun getItemCount(): Int {
        if (diff.currentList.isEmpty() || diff.currentList == null) return 0
        return diff.currentList.size
    }

    inner class PriceListViewHolder(val binding: ItemListPriceListRecycleViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setUpBinding(pricetList: PriceList, listener: (priceList: PriceList) -> Unit) {
            binding.idShopPriceListItem.text = "Id Shop: " + pricetList.shopIdShop
            binding.idProductPriceListItem.text = "Id Product: " + pricetList.productIdProduct
            binding.pricePriceListItem.text = pricetList.price.toString() + " $"
            if (!(pricetList.image.data.isEmpty() || pricetList.image.data.size == 0)) {
                binding.imagePriceListItem.setImageBitmap(
                    BitmapFactory.decodeByteArray(
                        pricetList.image.data,
                        0,
                        pricetList.image.data.size
                    )
                )
            }
            itemView.setOnClickListener {
                listener.let { it(pricetList) }
            }
        }
    }
}