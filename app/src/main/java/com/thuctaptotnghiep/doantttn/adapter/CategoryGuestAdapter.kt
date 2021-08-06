package com.thuctaptotnghiep.doantttn.adapter

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.thuctaptotnghiep.doantttn.Constant
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.api.response.Category
import com.thuctaptotnghiep.doantttn.databinding.ItemListCategoryRecycleViewGuestBinding
import com.thuctaptotnghiep.doantttn.ui.MainScreenGuest.MainGuestViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryGuestAdapter(
    val context: Context,
    val activity: Activity,
    val viewModel: MainGuestViewModel,
    val lifecycleOwner: LifecycleOwner
) :
    RecyclerView.Adapter<CategoryGuestAdapter.CategoryGuestAdapterViewHolder>() {



    var itemOnClickListener: ((category: Category) -> Unit)? = null
    var productByCategoryGuestAdapter: ProductByCategoryGuestAdapter= ProductByCategoryGuestAdapter()

    fun setItemCategoryAdapterGuestOnClickListener(listener: ((category: Category) -> Unit)) {
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


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryGuestAdapterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemListCategoryRecycleViewGuestBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.item_list_category_recycle_view_guest, parent, false
        )
        return CategoryGuestAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryGuestAdapterViewHolder, position: Int) {
        holder.setUpBinding(diff.currentList[position])
    }

    override fun getItemCount(): Int {
        if (diff.currentList.isEmpty() || diff.currentList == null) return 0
        return diff.currentList.size
    }

    inner class CategoryGuestAdapterViewHolder(val itemBinding: ItemListCategoryRecycleViewGuestBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {


        fun setUpBinding(category: Category) {
            itemBinding.idCategoryTextViewItem.text = category.idCategory
            itemBinding.categoryNameTextViewItem.text = category.name
            itemBinding.stateItem.text = "false"
            itemBinding.listProductByCategoryItemGuest.layoutManager = LinearLayoutManager(context)
            viewModel.listProductByCategory.observe(lifecycleOwner,{
                productByCategoryGuestAdapter.diff.submitList(it)
            })
            setShowOffBtn(category.idCategory)
        }

        fun setShowOffBtn(idCategory: String){
            itemBinding.dropDownIcon.setOnClickListener {
                TransitionManager.beginDelayedTransition(itemBinding.cartItemCategoryRecycleViewGuest,AutoTransition())
                if(itemBinding.stateItem.text == "false"){
                    getListProduct(idCategory)
                    itemBinding.listProductByCategoryItemGuest.adapter = productByCategoryGuestAdapter
                    itemBinding.stateItem.text = "true"
                    itemBinding.listProductByCategoryItemGuest.visibility = View.VISIBLE
                }
                else{
                    itemBinding.listProductByCategoryItemGuest.adapter = null
                    itemBinding.stateItem.text ="false"
                    itemBinding.listProductByCategoryItemGuest.visibility = View.GONE
                }
            }
        }

        fun getListProduct(idCategory:String) {
            CoroutineScope(Dispatchers.Default).launch {
                val pref = activity.getSharedPreferences(
                    Constant.SHARE_PREFERENCE_NAME,
                    Context.MODE_PRIVATE
                )
                val token = pref.getString("token", "null")!!
                if (token == "null") {
                    Snackbar.make(itemBinding.root, "token null", Snackbar.LENGTH_LONG)
                        .setBackgroundTint(Color.RED).show()
                } else {
                    viewModel.getListProductByCategory(token,idCategory)
                }
            }
        }

    }
}