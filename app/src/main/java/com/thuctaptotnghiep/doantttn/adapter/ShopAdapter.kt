package com.thuctaptotnghiep.doantttn.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.api.response.Shop
import com.thuctaptotnghiep.doantttn.databinding.ItemListShopRecycleViewBinding

class ShopAdapter : RecyclerView.Adapter<ShopAdapter.ShopViewHolder>() {

    var itemOnClickListener: ((shop: Shop) -> Unit)? = null
    fun setItemShopOnClickListener(listener: ((shop: Shop) -> Unit)) {
        this.itemOnClickListener = listener
    }

    var diffCallBack = object : DiffUtil.ItemCallback<Shop>() {
        override fun areItemsTheSame(oldItem: Shop, newItem: Shop): Boolean {
            return oldItem.idShop == newItem.idShop
        }

        override fun areContentsTheSame(oldItem: Shop, newItem: Shop): Boolean {
            return oldItem == newItem
        }
    }
    var diff = AsyncListDiffer(this, diffCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemListShopRecycleViewBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.item_list_shop_recycle_view,
            parent,
            false
        )
        return ShopViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        holder.setUpBinding(diff.currentList[position],itemOnClickListener!!)
    }

    override fun getItemCount(): Int {
        if (diff.currentList == null || diff.currentList.isNullOrEmpty()) return 0
        return diff.currentList.size
    }

    inner class ShopViewHolder(val binding: ItemListShopRecycleViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setUpBinding(shop: Shop, listener: (shop: Shop) -> Unit) {
            binding.idShopItemRecycle.text = shop.idShop
            binding.nameShopItemRecycle.text = shop.name
            binding.phoneShopItemRecycle.text = shop.phone
            binding.addressShopItemRecycle.text = shop.address
            itemView.setOnClickListener {
                listener.let {
                    it(shop)
                }
            }
        }
    }
}