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
import com.thuctaptotnghiep.doantttn.adapter.DetailShopProductAdapter
import com.thuctaptotnghiep.doantttn.databinding.FragmentDetailShopProductBinding
import com.thuctaptotnghiep.doantttn.dialog.LoadingDialog
import com.thuctaptotnghiep.doantttn.dialog.DetailShopProductAddDialog
import com.thuctaptotnghiep.doantttn.dialog.DetailShopProductEditDialog
import com.thuctaptotnghiep.doantttn.dialog.InformDialog
import com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.MainAdminActivity
import com.thuctaptotnghiep.doantttn.ui.MainScreenAdmin.MainAdminViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DetailShopProductFragment : Fragment() {

    lateinit var binding: FragmentDetailShopProductBinding
    lateinit var viewModel: MainAdminViewModel
    lateinit var detailShopProductAdapter: DetailShopProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.fragment_detail_shop_product,
                container,
                false
            )
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
        viewModel.listDetailShopProduct.observe(viewLifecycleOwner, Observer {
            detailShopProductAdapter.diff.submitList(it)
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
                loadingDialog.show(requireActivity().supportFragmentManager, "loading dialog")
                viewModel.getAllDetailShopProduct(token)
                /**
                 * Why i had to do this ???
                 * getDialog from loading dialog return null only when I call getDialog too early before
                 * onCreateDialog() run so i must delay 1 sec to wait onCreateDialog finish and
                 * call cancel()
                 */
                if (loadingDialog.dialog == null) {
                    delay(1000)
                    loadingDialog.cancelLoading()
                } else {
                    loadingDialog.cancelLoading()
                }
            }
        }
    }

    fun setUpRecycleView() {
        detailShopProductAdapter = DetailShopProductAdapter()
        detailShopProductAdapter.setItemDetailShopProductAdapterOnClickListener {
            val dialogEditPriceList = DetailShopProductEditDialog(it)
            dialogEditPriceList.show(
                requireActivity().supportFragmentManager,
                "dialog edit price list"
            )
        }
        binding.detailShopProductRecycleView.layoutManager = LinearLayoutManager(activity)
        binding.detailShopProductRecycleView.adapter = detailShopProductAdapter
    }

    fun setOnClickAddPriceListFloatingBtn() {
        binding.addDetailShopProductObjectFloatingBtn.setOnClickListener {
            if (viewModel.listShop.value.isNullOrEmpty()) {
                val informDialog =
                    InformDialog("fail", "List Shop Empty !!, Can't create")
                informDialog.show(requireActivity().supportFragmentManager, "error dialog")
            } else if (viewModel.listProduct.value.isNullOrEmpty()) {
                val informDialog =
                    InformDialog("fail", "List Product Empty !!, Can't create")
                informDialog.show(requireActivity().supportFragmentManager, "error dialog")
            } else {
                val dialogAddPriceList = DetailShopProductAddDialog()
                dialogAddPriceList.show(
                    requireActivity().supportFragmentManager,
                    "dialog add price list"
                )
            }
        }
    }

}