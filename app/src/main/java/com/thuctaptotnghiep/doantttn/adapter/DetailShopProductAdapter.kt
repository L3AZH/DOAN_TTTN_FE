package com.thuctaptotnghiep.doantttn.adapter

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.api.response.DetailShopProduct
import com.thuctaptotnghiep.doantttn.databinding.ItemListDetailShopProductRecycleViewBinding

class DetailShopProductAdapter() :
    RecyclerView.Adapter<DetailShopProductAdapter.DetailShopProductViewHolder>() {

    var itemOnClickListener: ((detailShopProduct: DetailShopProduct) -> Unit)? = null

    fun setItemDetailShopProductAdapterOnClickListener(listener: ((detailShopProduct: DetailShopProduct) -> Unit)) {
        this.itemOnClickListener = listener
    }


    var differCallBack = object : DiffUtil.ItemCallback<DetailShopProduct>() {
        override fun areItemsTheSame(
            oldItem: DetailShopProduct,
            newItem: DetailShopProduct
        ): Boolean {
            return (oldItem.productIdProduct == newItem.productIdProduct && oldItem.shopIdShop == newItem.shopIdShop)
        }

        override fun areContentsTheSame(
            oldItem: DetailShopProduct,
            newItem: DetailShopProduct
        ): Boolean {
            return oldItem == newItem
        }
    }

    val diff = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailShopProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemListDetailShopProductRecycleViewBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.item_list_detail_shop_product_recycle_view,
            parent,
            false
        )
        return DetailShopProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailShopProductViewHolder, position: Int) {
        holder.setUpBinding(diff.currentList[position], itemOnClickListener!!)
    }

    override fun getItemCount(): Int {
        if (diff.currentList.isEmpty()) return 0
        return diff.currentList.size
    }

    inner class DetailShopProductViewHolder(val binding: ItemListDetailShopProductRecycleViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setUpBinding(
            pricetList: DetailShopProduct,
            listener: (detailShopProduct: DetailShopProduct) -> Unit
        ) {
            binding.idShopDetailShopProductItem.text = "Id Shop: " + pricetList.shopIdShop
            binding.idProductDetailShopProductItem.text =
                "Id Product: " + pricetList.productIdProduct
            binding.priceDetailShopProductItem.text = pricetList.price.toString() + " $"
            if (!(pricetList.image.data.isEmpty() || pricetList.image.data.size == 0)) {
                binding.imageDetailShopProductItem.setImageBitmap(
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