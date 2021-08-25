package com.thuctaptotnghiep.doantttn.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.api.response.Category
import com.thuctaptotnghiep.doantttn.databinding.ItemListCategoryRecycleViewBinding

class CategoryAdapter() : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    var itemOnClickListener:((category:Category)->Unit)? = null

    fun setItemCategoryAdapterOnClickListener(listener: ((category:Category)->Unit)){
        this.itemOnClickListener = listener
    }

    var differCallBack = object : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.idCategory == newItem.idCategory
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }

    val diff = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemListCategoryRecycleViewBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.item_list_category_recycle_view,
            parent,
            false
        )
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.setUpBinding(diff.currentList[position],itemOnClickListener!!)
    }

    override fun getItemCount(): Int {
        if (diff.currentList.isEmpty()) return 0
        else return diff.currentList.size
    }

    inner class CategoryViewHolder(val binding: ItemListCategoryRecycleViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setUpBinding(category: Category,itemClickListener:((category:Category)->Unit)) {
            binding.uuidTextViewItemCategory.text = category.idCategory
            binding.nameTextViewItemCategory.text = category.name
            itemView.setOnClickListener {
                itemClickListener.let {
                    it(category)
                }
            }
        }
    }
}