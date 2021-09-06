package com.thuctaptotnghiep.doantttn.adapter

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.databinding.ItemConfirmCartRecycleViewGuestBinding
import com.thuctaptotnghiep.doantttn.db.model.Cart
import com.thuctaptotnghiep.doantttn.utils.CurrencyConvert

class CartConfirmAdapter : RecyclerView.Adapter<CartConfirmAdapter.CartConfirmViewHolder>() {

    val diffcallBack = object : DiffUtil.ItemCallback<Cart>() {
        override fun areItemsTheSame(oldItem: Cart, newItem: Cart): Boolean {
            return oldItem.idCart == newItem.idCart
        }

        override fun areContentsTheSame(oldItem: Cart, newItem: Cart): Boolean {
            return oldItem == newItem
        }
    }

    val diff = AsyncListDiffer(this, diffcallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartConfirmViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemConfirmCartRecycleViewGuestBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.item_confirm_cart_recycle_view_guest, parent, false
        )
        return CartConfirmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartConfirmViewHolder, position: Int) {
        holder.setUpBinding(diff.currentList[position])
    }

    override fun getItemCount(): Int {
        if (diff.currentList.isEmpty()) return 0
        return diff.currentList.size
    }

    inner class CartConfirmViewHolder(val itemBinding: ItemConfirmCartRecycleViewGuestBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun setUpBinding(cart: Cart) {
            itemBinding.imageCartConfirmItem.setImageBitmap(
                BitmapFactory.decodeByteArray(
                    cart.image,
                    0,
                    cart.image.size
                )
            )
            itemBinding.nameProductConfirmCartItem.text = "Name: " + cart.nameProduct
            itemBinding.nameShopConfirmCartItem.text = "Shop: " + cart.nameShop
            itemBinding.priceProductConfirmCartItem.text =
                "Price: " + CurrencyConvert.convertCurrencyToString(cart.price)
            itemBinding.amountConfirmCartItem.text = "Amount: " + cart.amount.toString()
        }
    }
}