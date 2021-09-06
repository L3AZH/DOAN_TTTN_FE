package com.thuctaptotnghiep.doantttn.ui.MainScreenGuest.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.thuctaptotnghiep.doantttn.utils.Constant
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.adapter.DetailShopProductByProductAdapter
import com.thuctaptotnghiep.doantttn.api.response.DetailShopProductFullInformation
import com.thuctaptotnghiep.doantttn.databinding.FragmentFindProductBinding
import com.thuctaptotnghiep.doantttn.dialog.LoadingDialog
import com.thuctaptotnghiep.doantttn.ui.MainScreenGuest.MainGuestActivity
import com.thuctaptotnghiep.doantttn.ui.MainScreenGuest.MainGuestViewModel
import kotlinx.coroutines.*

class FindProductFragment : Fragment() {

    lateinit var binding: FragmentFindProductBinding
    lateinit var viewModel: MainGuestViewModel
    lateinit var detailShopProductAdapter: DetailShopProductByProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_find_product,
            container,
            false
        )
        viewModel = (activity as MainGuestActivity).viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        intialViewModel()
        setOnClickSearchBtn()
    }

    override fun onStop() {
        super.onStop()
        viewModel.clearListPriceListByProduct()
    }

    fun intialViewModel() {
        viewModel.listListDetailShopProductByProduct.observe(viewLifecycleOwner, {
            detailShopProductAdapter.diff.submitList(it)
        })
    }

    fun setUpAdapter() {
        detailShopProductAdapter = DetailShopProductByProductAdapter()
        binding.listProductRecycleViewSearchResult.layoutManager = LinearLayoutManager(context)
        binding.listProductRecycleViewSearchResult.adapter = detailShopProductAdapter
        detailShopProductAdapter.setDetailShopProductByProductItemOnClickListener {
            setOnClickItemResultFound(it)
        }
    }

    fun setOnClickSearchBtn() {
        binding.nameProductSearchEditText.addTextChangedListener(DebouncingQueryTextListener(this.lifecycle){
            it?.let {
                viewModel.getListPriceListByNameProduct(
                    Constant.getToken(requireContext())!!,
                    binding.nameProductSearchEditText.text.toString()
                )
            }
        })
        binding.nameProductSearchTextInputLayout.setEndIconOnClickListener {
            CoroutineScope(Dispatchers.Default).launch {
                val prefs = requireActivity().getSharedPreferences(
                    Constant.SHARE_PREFERENCE_NAME,
                    Context.MODE_PRIVATE
                )
                val token = prefs.getString("token", "null")
                if (token == "null") {
                    Snackbar.make(binding.root, "Token null", Snackbar.LENGTH_LONG)
                        .setBackgroundTint(
                            Color.RED
                        ).show()
                } else {
                    val loadingDialog = LoadingDialog()
                    loadingDialog.show(requireActivity().supportFragmentManager,"loading dialog")
                    viewModel.getListPriceListByNameProduct(
                        token!!,
                        binding.nameProductSearchEditText.text.toString()
                    )
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
    }

    fun setOnClickItemResultFound(detailShopProductFullInformation: DetailShopProductFullInformation) {
        val goToProductInformationFrag =
            FindProductFragmentDirections.actionFindProductFragmentToInformationProductFragment(
                detailShopProductFullInformation
            )
        findNavController().navigate(goToProductInformationFrag)
    }

    internal  class DebouncingQueryTextListener(
        lifeCycle:Lifecycle,
        private val onDebouncingQueryTextListener:(String?)->Unit
    ):TextWatcher,LifecycleObserver{

        var debouncePeriod:Long = 500

        private val coroutineScope = CoroutineScope(Dispatchers.Main)

        private var searchJob:Job? = null

        init {
            lifeCycle.addObserver(this)
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            searchJob?.cancel()
            searchJob = coroutineScope.launch {
                s?.let {
                    delay(debouncePeriod)
                    onDebouncingQueryTextListener(it.toString())
                }
            }
        }

        override fun afterTextChanged(s: Editable?) {
            Log.e("L#AZH", "beforeTextChanged: " )
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            Log.e("L#AZH", "beforeTextChanged: " )
        }

    }
}