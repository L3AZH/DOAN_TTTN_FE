package com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
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
import com.thuctaptotnghiep.doantttn.dialog.LoadingDialog
import com.thuctaptotnghiep.doantttn.dialog.PriceListAddDialog
import com.thuctaptotnghiep.doantttn.dialog.PriceListEditDialog
import com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.MainAdminActivity
import com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.MainAdminViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PriceListFragment : Fragment() {

    lateinit var binding: FragmentPriceListBinding
    lateinit var viewModel: MainAdminViewModel
    lateinit var priceListAdapter: PriceListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_price_list, container, false)
        viewModel = (requireActivity() as MainAdminActivity).viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycleView()
        intialViewModel()
        setOnClickAddPriceListFloatingBtn()
    }

    override fun onStop() {
        super.onStop()
        viewModel.clearListPriceList()
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
                val loadingDialog = LoadingDialog()
                loadingDialog.show(requireActivity().supportFragmentManager,"loading dialog")
                viewModel.getAllPriceList(token)
                /**
                 * Why i had to do this ???
                 * getDialog from loading dialog return null only when I call getDialog too early before
                 * onCreateDialog() run so i must delay 1 sec to wait onCreateDialog finish and
                 * call cancel()
                 */
                if(loadingDialog.dialog == null){
                    delay(1000)
                    loadingDialog.cancelLoading()
                }
                else{
                    loadingDialog.cancelLoading()
                }
            }
        }
    }

    fun setUpRecycleView() {
        priceListAdapter = PriceListAdapter()
        priceListAdapter.setItemPriceListAdapterOnClickListener {
            val dialogEditPriceList = PriceListEditDialog(it)
            dialogEditPriceList.show(
                requireActivity().supportFragmentManager,
                "dialog edit price list"
            )
        }
        binding.priceListRecycleView.layoutManager = LinearLayoutManager(activity)
        binding.priceListRecycleView.adapter = priceListAdapter
    }

    fun setOnClickAddPriceListFloatingBtn() {
        binding.addPriceListObjectFloatingBtn.setOnClickListener {
            val dialogAddPriceList = PriceListAddDialog()
            dialogAddPriceList.show(
                requireActivity().supportFragmentManager,
                "dialog add price list"
            )
        }
    }

}