package com.thuctaptotnghiep.doantttn.adapter

import android.app.Activity
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.databinding.ItemCartRecycleViewGuestBinding
import com.thuctaptotnghiep.doantttn.db.model.Cart
import com.thuctaptotnghiep.doantttn.utils.CurrencyConvert
import kotlinx.coroutines.currentCoroutineContext

class CartAdapter(val context:Context) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    var onClickMinusBtnCallBack: ((cart: Cart) -> Unit)? = null
    var onClickPlusBtnCallBack: ((cart: Cart) -> Unit)? = null

    fun setOnClickMinusCallBack(callback: (cart: Cart) -> Unit) {
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

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.setUpBinding(
            position,
            diff.currentList[position],
            onClickMinusBtnCallBack!!,
            onClickPlusBtnCallBack!!,
            context
        )
    }

    override fun getItemCount(): Int {
        if (diff.currentList.isNullOrEmpty()) return 0
        return diff.currentList.size
    }

    inner class CartViewHolder(val binding: ItemCartRecycleViewGuestBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.R)
        fun setUpBinding(
            position: Int,
            cart: Cart,
            callbackMinus: ((cart: Cart) -> Unit),
            callbackPlus: ((cart: Cart) -> Unit),
            context: Context
        ) {
            binding.imageCartItem.setImageBitmap(
                BitmapFactory.decodeByteArray(
                    cart.image,
                    0,
                    cart.image.size
                )
            )

            val displayMetrics = DisplayMetrics()
            context.display!!.getRealMetrics(displayMetrics)
            val mPaint = Paint()
            mPaint.textSize = 16f

            val priceText = CurrencyConvert.convertCurrencyToString(cart.price)

            val sizeTextNameProduct = mPaint.measureText(cart.nameProduct,0,cart.nameProduct.length)
            val sizeTextNameShop = mPaint.measureText(cart.nameShop,0,cart.nameShop.length)
            val sizeTextPrice = mPaint.measureText(priceText,0,priceText.length)
            val sizeTextAmount = mPaint.measureText(cart.amount.toString(),0,cart.amount.toString().length)

            val listSize:MutableList<Float> = mutableListOf()
            listSize.add(sizeTextNameProduct)
            listSize.add(sizeTextNameShop)
            listSize.add(sizeTextPrice)

            val sizeMax:Float = (listSize.maxOrNull() ?:0) as Float


            if(sizeMax > (displayMetrics.widthPixels-80-10-64-10-sizeTextAmount-64)/3){
                val maxWidth = (displayMetrics.widthPixels-80-10-64-10-sizeTextAmount-64)/3
                binding.nameProductCartItem.maxWidth = maxWidth.toInt()
                binding.nameShopCartItem.maxWidth = maxWidth.toInt()
                binding.priceProductCartItem.maxWidth = maxWidth.toInt()
            }

            binding.nameProductCartItem.text = cart.nameProduct
            binding.nameShopCartItem.text = cart.nameShop
            binding.priceProductCartItem.text = priceText
            binding.amountCartItem.text = cart.amount.toString()
            binding.minusCartBtn.setOnClickListener {
                callbackMinus.let {
                    it(cart)
                    notifyItemChanged(position)
                }
            }
            binding.plusCartBtn.setOnClickListener {
                callbackPlus.let {
                    it(cart)
                    notifyItemChanged(position)
                }
            }
        }
    }
}