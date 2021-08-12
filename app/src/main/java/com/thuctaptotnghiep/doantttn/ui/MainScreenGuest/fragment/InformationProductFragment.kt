package com.thuctaptotnghiep.doantttn.ui.MainScreenGuest.fragment

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.thuctaptotnghiep.doantttn.Constant
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.databinding.FragmentInformationProductBinding
import com.thuctaptotnghiep.doantttn.dialog.InformDialog
import com.thuctaptotnghiep.doantttn.ui.MainScreenGuest.MainGuestActivity
import com.thuctaptotnghiep.doantttn.ui.MainScreenGuest.MainGuestViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class InformationProductFragment : Fragment() {

    lateinit var binding: FragmentInformationProductBinding
    lateinit var viewModel: MainGuestViewModel
    val args: InformationProductFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_information_product,
            container,
            false
        )
        viewModel = (activity as MainGuestActivity).viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpBindingDetail()
        setOnclickAddToCartBtn()
    }

    fun setUpBindingDetail() {
        binding.imagePriceListDetail.setImageBitmap(
            BitmapFactory.decodeByteArray(
                args.priceListFullInfoItem.image.data,
                0,
                args.priceListFullInfoItem.image.data.size
            )
        )
        binding.nameProductDetail.text = "Name: " + args.priceListFullInfoItem.nameProduct
        binding.priceProductDetail.text =
            "Price: " + args.priceListFullInfoItem.price.toString() + " $"
        binding.nameShopDetail.text = "Shop name: " + args.priceListFullInfoItem.nameShop
        binding.addressShopDetail.text = "Address: " + args.priceListFullInfoItem.addressShop
        binding.phoneShopDetail.text = "Phone: " + args.priceListFullInfoItem.phoneShop
    }

    fun setOnclickAddToCartBtn() {
        binding.addToCartBtn.setOnClickListener {
            val prefs = requireActivity().getSharedPreferences(
                Constant.SHARE_PREFERENCE_NAME,
                Context.MODE_PRIVATE
            )
            val email = prefs.getString("email", "null")
            if (email == "null") {
                Snackbar.make(binding.root, "RefreshToken NULL", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(Color.RED).show()
            }
            if (checkValidAmount(binding.amountProductDetailTextInputEditText.text.toString())) {
                CoroutineScope(Dispatchers.Default).launch {
                    val rs = viewModel.addToCart(
                        args.priceListFullInfoItem,
                        binding.amountProductDetailTextInputEditText.text.toString().toInt(),
                        email!!
                    ).await()
                    if ((rs["flag"] as Boolean)) {
                        val succesDialog = InformDialog("success", rs["message"].toString())
                        succesDialog.show(
                            requireActivity().supportFragmentManager,
                            "Success dialog inform"
                        )
                    } else {
                        val succesDialog = InformDialog("fail", rs["message"].toString())
                        succesDialog.show(
                            requireActivity().supportFragmentManager,
                            "Fail dialog inform"
                        )
                    }
                }
            }
        }
    }

    fun checkValidAmount(amount: String): Boolean {
        try {
            val rs = amount.toInt()
            if (rs > 10) {
                Snackbar.make(
                    binding.root,
                    "Amount cant be greater than 10!!",
                    Snackbar.LENGTH_LONG
                ).setBackgroundTint(Color.RED).show()
                return false
            }
            if (rs == 0) {
                Snackbar.make(
                    binding.root,
                    "Amount is zero !!",
                    Snackbar.LENGTH_LONG
                ).setBackgroundTint(Color.RED).show()
                return false
            }
            if (rs < 0) {
                Snackbar.make(
                    binding.root,
                    "Amount Invalid !!",
                    Snackbar.LENGTH_LONG
                ).setBackgroundTint(Color.RED).show()
                return false
            }
            return true
        } catch (ex: Exception) {
            ex.printStackTrace()
            Snackbar.make(
                binding.root,
                "Invalid Amount !!",
                Snackbar.LENGTH_LONG
            ).setBackgroundTint(Color.RED).show()
            return false
        }
    }
}