package com.thuctaptotnghiep.doantttn.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.os.Build
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.databinding.ItemConfirmCartRecycleViewGuestBinding
import com.thuctaptotnghiep.doantttn.db.model.Cart
import com.thuctaptotnghiep.doantttn.utils.CurrencyConvert

class CartConfirmAdapter(val context:Context) : RecyclerView.Adapter<CartConfirmAdapter.CartConfirmViewHolder>() {

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
        holder.setUpBinding(diff.currentList[position],context)
    }

    override fun getItemCount(): Int {
        if (diff.currentList.isEmpty()) return 0
        return diff.currentList.size
    }

    inner class CartConfirmViewHolder(val itemBinding: ItemConfirmCartRecycleViewGuestBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        @RequiresApi(Build.VERSION_CODES.R)
        fun setUpBinding(cart: Cart, context: Context) {
            /*itemBinding.imageCartConfirmItem.setImageBitmap(
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
            itemBinding.amountConfirmCartItem.text = "Amount: " + cart.amount.toString()*/

            val displayMetrics = DisplayMetrics()
            context.display!!.getRealMetrics(displayMetrics)
            val mPaint = Paint()
            mPaint.textSize = 16f

            val priceText = CurrencyConvert.convertCurrencyToString(cart.price)

            val sizeTextNameProduct =
                mPaint.measureText(cart.nameProduct, 0, cart.nameProduct.length)
            val sizeTextNameShop =
                mPaint.measureText(cart.nameShop, 0, cart.nameShop.length)
            val sizeTextPrice = mPaint.measureText(priceText, 0, priceText.length)
            val sizeTextAmount = mPaint.measureText(
                "Amount: "+cart.amount.toString(),
                0,
                cart.amount.toString().length + "Amount: ".length
            )

            val listSize: MutableList<Float> = mutableListOf()
            listSize.add(sizeTextNameProduct)
            listSize.add(sizeTextNameShop)
            listSize.add(sizeTextPrice)

            val sizeMax: Float = (listSize.maxOrNull() ?: 0) as Float


            if (sizeMax > (displayMetrics.widthPixels - 80 - 10 - 10 - sizeTextAmount - 64) / 3) {
                val maxWidth =
                    (displayMetrics.widthPixels - 80 - 10 - 64 - 10 - sizeTextAmount - 64) / 3
                itemBinding.nameProductConfirmCartItem.maxWidth = maxWidth.toInt()
                itemBinding.nameShopConfirmCartItem.maxWidth = maxWidth.toInt()
                itemBinding.priceProductConfirmCartItem.maxWidth = maxWidth.toInt()
            }

            itemBinding.nameProductConfirmCartItem.text = "Name: " + cart.nameProduct
            itemBinding.nameShopConfirmCartItem.text = "Shop: " + cart.nameShop
            itemBinding.amountConfirmCartItem.text = "Amount: " + cart.amount.toString()
            itemBinding.priceProductConfirmCartItem.text = "Price: " + priceText
            itemBinding.imageCartConfirmItem.setImageBitmap(
                BitmapFactory.decodeByteArray(
                    cart.image,
                    0,
                    cart.image.size
                )
            )
        }
    }
}