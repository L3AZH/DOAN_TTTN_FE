package com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.thuctaptotnghiep.doantttn.Constant
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.adapter.CategoryAdapter
import com.thuctaptotnghiep.doantttn.adapter.PriceListAdapter
import com.thuctaptotnghiep.doantttn.databinding.FragmentPriceListBinding
import com.thuctaptotnghiep.doantttn.dialog.PriceListAddDialog
import com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.MainAdminActivity
import com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.MainAdminViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PriceListFragment : Fragment() {

    lateinit var binding:FragmentPriceListBinding
    lateinit var viewModel: MainAdminViewModel
    lateinit var priceListAdapter:PriceListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_price_list,container,false)
        viewModel = (requireActivity() as MainAdminActivity).viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycleView()
        intialViewModel()
        setOnClickAddPriceListFloatingBtn()
    }

    fun intialViewModel() {
        viewModel.listPriceList.observe(viewLifecycleOwner, Observer {
            priceListAdapter.diff.submitList(it)
        })
        CoroutineScope(Dispatchers.Default).launch {
            val pref = requireActivity().getSharedPreferences(
                Constant.SHARE_PREFERENCE_NAME,
                Context.MODE_PRIVATE
            )
            val token = pref.getString("token", "null")!!
            if (token == "null") {
                Snackbar.make(binding.root, "token null", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(Color.RED).show()
            } else {
                viewModel.getAllPriceList(token)
            }
        }
    }

    fun setUpRecycleView() {
        priceListAdapter = PriceListAdapter()
        priceListAdapter.setItemPriceListAdapterOnClickListener {

        }
        binding.priceListRecycleView.layoutManager = LinearLayoutManager(activity)
        binding.priceListRecycleView.adapter = priceListAdapter
    }

    fun setOnClickAddPriceListFloatingBtn(){
        binding.addPriceListObjectFloatingBtn.setOnClickListener {
            val dialogAddPriceList = PriceListAddDialog()
            dialogAddPriceList.show(requireActivity().supportFragmentManager,"dialog add price list")
        }
    }

}