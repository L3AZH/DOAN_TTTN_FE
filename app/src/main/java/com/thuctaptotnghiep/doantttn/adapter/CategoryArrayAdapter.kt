package com.thuctaptotnghiep.doantttn.adapter

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.Filter
import androidx.databinding.DataBindingUtil
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.api.response.Category
import com.thuctaptotnghiep.doantttn.databinding.CategorySpinnerItemBinding

class CategoryArrayAdapter( context: Context, var categoryList: List<Category>) :
    ArrayAdapter<Category>(context,0,categoryList){

    fun getItemId(idCategory:String):Category?{
        for(element in categoryList){
            if(element.idCategory == idCategory) return element
        }
        return null
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view:View
        val binding:CategorySpinnerItemBinding
        if(convertView == null){
            val layoutInflater = LayoutInflater.from(parent!!.context)
            binding = DataBindingUtil.inflate(layoutInflater,R.layout.category_spinner_item,null,false)
            view = binding.root
            view.tag = binding
        }
        else{
            view = convertView
            binding = view.tag as CategorySpinnerItemBinding
        }
        binding.idCategorySpinnerItem.text = categoryList.elementAt(position).idCategory
        binding.nameCategorySpinnerItem.text = categoryList[position].name
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view:View
        val binding:CategorySpinnerItemBinding
        if(convertView == null){
            val layoutInflater = LayoutInflater.from(parent!!.context)
            binding = DataBindingUtil.inflate(layoutInflater,R.layout.category_spinner_item,null,false)
            view = binding.root
            view.tag = binding
        }
        else{
            view = convertView
            binding = view.tag as CategorySpinnerItemBinding
        }
        binding.idCategorySpinnerItem.text = categoryList.elementAt(position).idCategory
        binding.nameCategorySpinnerItem.text = categoryList[position].name
        return view
    }
}