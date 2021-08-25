package com.thuctaptotnghiep.doantttn.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.api.response.Product
import com.thuctaptotnghiep.doantttn.databinding.CategorySpinnerItemBinding
import com.thuctaptotnghiep.doantttn.databinding.ProductSpinnerItemBinding
import com.thuctaptotnghiep.doantttn.databinding.ShopSpinnerItemBinding

class ProductArrayAdapter(context: Context, var productList: List<Product>) :
    ArrayAdapter<Product>(context,0,productList){

    fun getItemId(idProduct:String): Product?{
        for(element in productList){
            if(element.idProduct == idProduct) return element
        }
        return null
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view: View
        val binding: ProductSpinnerItemBinding
        if(convertView == null){
            val layoutInflater = LayoutInflater.from(parent.context)
            binding = DataBindingUtil.inflate(layoutInflater,
                R.layout.product_spinner_item,null,false)
            view = binding.root
            view.tag = binding
        }
        else{
            view = convertView
            binding = view.tag as ProductSpinnerItemBinding
        }
        binding.idProductSpinnerItem.text = productList.elementAt(position).idProduct
        binding.nameProductSpinnerItem.text = productList[position].name
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view: View
        val binding: ProductSpinnerItemBinding
        if(convertView == null){
            val layoutInflater = LayoutInflater.from(parent.context)
            binding = DataBindingUtil.inflate(layoutInflater,
                R.layout.product_spinner_item,null,false)
            view = binding.root
            view.tag = binding
        }
        else{
            view = convertView
            binding = view.tag as ProductSpinnerItemBinding
        }
        binding.idProductSpinnerItem.text = productList.elementAt(position).idProduct
        binding.nameProductSpinnerItem.text = productList[position].name
        return view
    }
}