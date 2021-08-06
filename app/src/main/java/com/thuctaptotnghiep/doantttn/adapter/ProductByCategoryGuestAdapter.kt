package com.thuctaptotnghiep.doantttn.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.api.response.Product
import com.thuctaptotnghiep.doantttn.databinding.ItemListProductByCategoryRecycleViewGuestBinding

class ProductByCategoryGuestAdapter :
    RecyclerView.Adapter<ProductByCategoryGuestAdapter.ProductByCategoryGuestViewHolder>() {

    var itemOnClickListener:((product:Product)->Unit)? = null

    fun setItemProductByCategoryAdapterGuestOnClickListener(listener: ((product:Product)->Unit) ){
        this.itemOnClickListener = listener
    }


    var differCallBack = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.idProduct == newItem.idProduct
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    val diff = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductByCategoryGuestViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemListProductByCategoryRecycleViewGuestBinding = DataBindingUtil.inflate(layoutInflater,
            R.layout.item_list_product_by_category_recycle_view_guest,parent,false)
        return ProductByCategoryGuestViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductByCategoryGuestViewHolder, position: Int) {
        holder.setUpBinding(diff.currentList[position])
    }

    override fun getItemCount(): Int {
        if(diff.currentList == null || diff.currentList.isEmpty()) return 0
        return diff.currentList.size
    }

    inner class ProductByCategoryGuestViewHolder(val binding: ItemListProductByCategoryRecycleViewGuestBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setUpBinding(product: Product) {
            binding.nameProductByCategoryTextView.text = product.name
            binding.idProductByCategoryTextView.text = product.idProduct
        }
    }
}