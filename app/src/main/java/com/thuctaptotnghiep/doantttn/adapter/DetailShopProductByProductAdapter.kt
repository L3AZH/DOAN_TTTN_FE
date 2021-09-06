package com.thuctaptotnghiep.doantttn.adapter

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.api.response.DetailShopProductFullInformation
import com.thuctaptotnghiep.doantttn.databinding.ItemListDetailShopProductByProductRecycleViewGuestBinding
import com.thuctaptotnghiep.doantttn.utils.CurrencyConvert
import kotlinx.coroutines.currentCoroutineContext

class DetailShopProductByProductAdapter :
    RecyclerView.Adapter<DetailShopProductByProductAdapter.PriceListByProductViewHolder>() {


    var itemOnClickListener: ((DetailShopProductFullInformation) -> Unit)? = null

    fun setDetailShopProductByProductItemOnClickListener(listener: ((DetailShopProductFullInformation) -> Unit)) {
        this.itemOnClickListener = listener
    }

    var diffCalllBack = object : DiffUtil.ItemCallback<DetailShopProductFullInformation>() {
        override fun areItemsTheSame(
            oldItem: DetailShopProductFullInformation,
            newItem: DetailShopProductFullInformation
        ): Boolean {
            return (oldItem.idProduct == newItem.idProduct && oldItem.idShop == newItem.idShop)
        }

        override fun areContentsTheSame(
            oldItem: DetailShopProductFullInformation,
            newItem: DetailShopProductFullInformation
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
        val binding: ItemListDetailShopProductByProductRecycleViewGuestBinding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.item_list_detail_shop_product_by_product_recycle_view_guest, parent, false
            )
        return PriceListByProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PriceListByProductViewHolder, position: Int) {
        holder.setUpBinding(diff.currentList[position], itemOnClickListener!!)
    }

    override fun getItemCount(): Int {
        if (diff.currentList.isNullOrEmpty()) return 0
        return diff.currentList.size
    }

    inner class PriceListByProductViewHolder(val binding: ItemListDetailShopProductByProductRecycleViewGuestBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setUpBinding(
            detailShopProductFullInformation: DetailShopProductFullInformation,
            listener: (DetailShopProductFullInformation) -> Unit
        ) {
            binding.nameProductTextView.text =
                "Name Product: " + detailShopProductFullInformation.nameProduct
            binding.nameShopTextView.text = "Shop: " + detailShopProductFullInformation.nameShop
            binding.priceProductTextView.text =
                "Price: " + CurrencyConvert.convertCurrencyToString(detailShopProductFullInformation.price)
            binding.imageDetailShopProduct.setImageBitmap(
                BitmapFactory.decodeByteArray(
                    detailShopProductFullInformation.image.data,
                    0,
                    detailShopProductFullInformation.image.data.size
                )
            )
            itemView.setOnClickListener {
                listener.let {
                    it(detailShopProductFullInformation)
                }
            }
        }

    }
}