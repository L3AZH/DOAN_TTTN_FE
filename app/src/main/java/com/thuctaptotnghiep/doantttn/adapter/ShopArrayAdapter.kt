package com.thuctaptotnghiep.doantttn.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.api.response.Shop
import com.thuctaptotnghiep.doantttn.databinding.CategorySpinnerItemBinding
import com.thuctaptotnghiep.doantttn.databinding.ShopSpinnerItemBinding

class ShopArrayAdapter(context: Context, var shopList: List<Shop>) :
    ArrayAdapter<Shop>(context,0,shopList){

    fun getItemId(idShop:String): Shop?{
        for(element in shopList){
            if(element.idShop == idShop) return element
        }
        return null
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view: View
        val binding: ShopSpinnerItemBinding
        if(convertView == null){
            val layoutInflater = LayoutInflater.from(parent.context)
            binding = DataBindingUtil.inflate(layoutInflater,
                R.layout.shop_spinner_item,null,false)
            view = binding.root
            view.tag = binding
        }
        else{
            view = convertView
            binding = view.tag as ShopSpinnerItemBinding
        }
        binding.idShopSpinnerItem.text = shopList.elementAt(position).idShop
        binding.nameShopSpinnerItem.text = shopList[position].name
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view: View
        val binding: ShopSpinnerItemBinding
        if(convertView == null){
            val layoutInflater = LayoutInflater.from(parent.context)
            binding = DataBindingUtil.inflate(layoutInflater,
                R.layout.shop_spinner_item,null,false)
            view = binding.root
            view.tag = binding
        }
        else{
            view = convertView
            binding = view.tag as ShopSpinnerItemBinding
        }
        binding.idShopSpinnerItem.text = shopList.elementAt(position).idShop
        binding.nameShopSpinnerItem.text = shopList[position].name
        return view
    }
}