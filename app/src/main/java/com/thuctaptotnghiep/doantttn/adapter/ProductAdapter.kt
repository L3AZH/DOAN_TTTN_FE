package com.thuctaptotnghiep.doantttn.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.api.response.Category
import com.thuctaptotnghiep.doantttn.api.response.Product
import com.thuctaptotnghiep.doantttn.databinding.FragmentProductBinding
import com.thuctaptotnghiep.doantttn.databinding.ItemListProductRecycleViewBinding

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    var differCallBack = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.idProduct == newItem.idProduct
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    val diff = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemListProductRecycleViewBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.item_list_product_recycle_view,
            parent,
            false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.setUpBinding(diff.currentList[position])
    }

    override fun getItemCount(): Int {
        if (diff.currentList.isEmpty() || diff.currentList == null) return 0
        return diff.currentList.size
    }

    inner class ProductViewHolder(val binding: ItemListProductRecycleViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setUpBinding(product: Product) {
            binding.uuidTextViewItemProduct.text = product.idProduct
            binding.nameTextViewItemProduct.text = product.name
            binding.idCategoryTextViewItemProduct.text = product.CategoryIdCategory
        }
    }
}