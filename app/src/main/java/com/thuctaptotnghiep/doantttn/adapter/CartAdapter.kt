package com.thuctaptotnghiep.doantttn.adapter

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.databinding.ItemCartRecycleViewGuestBinding
import com.thuctaptotnghiep.doantttn.db.model.Cart

class CartAdapter : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    var onClickMinusBtnCallBack: ((cart: Cart) -> Unit)? = null
    var onClickPlusBtnCallBack: ((cart: Cart) -> Unit)? = null

    fun setOnClickMinusCallBack(callback: ((cart: Cart) -> Unit)) {
        this.onClickMinusBtnCallBack = callback
    }

    fun setOnClickPlusCallBack(callback: (cart: Cart) -> Unit) {
        this.onClickPlusBtnCallBack = callback
    }

    val diffCallBack = object : DiffUtil.ItemCallback<Cart>() {
        override fun areContentsTheSame(oldItem: Cart, newItem: Cart): Boolean {
            return oldItem.idCart == newItem.idCart
        }

        override fun areItemsTheSame(oldItem: Cart, newItem: Cart): Boolean {
            return oldItem == newItem
        }
    }

    val diff = AsyncListDiffer(this, diffCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemCartRecycleViewGuestBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.item_cart_recycle_view_guest,
            parent,
            false
        )
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.setUpBinding(
            diff.currentList[position],
            onClickMinusBtnCallBack!!,
            onClickPlusBtnCallBack!!
        )
    }

    override fun getItemCount(): Int {
        if (diff.currentList == null || diff.currentList.isEmpty()) return 0
        return diff.currentList.size
    }

    inner class CartViewHolder(val binding: ItemCartRecycleViewGuestBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setUpBinding(
            cart: Cart,
            callbackMinus: ((cart: Cart) -> Unit),
            callbackPlus: (cart: Cart) -> Unit
        ) {
            binding.imageCartItem.setImageBitmap(
                BitmapFactory.decodeByteArray(
                    cart.image,
                    0,
                    cart.image.size
                )
            )
            binding.nameProductCartItem.text = cart.nameProduct
            binding.nameShopCartItem.text = cart.nameShop
            binding.priceProductCartItem.text = cart.price.toString()
            binding.amountCartItem.text = cart.amount.toString()
            binding.minusCartBtn.setOnClickListener {
                callbackMinus.let {
                    it(cart)
                }
            }
            binding.plusCartBtn.setOnClickListener {
                callbackPlus.let {
                    it(cart)
                }
            }
        }
    }
}