package com.thuctaptotnghiep.doantttn.ui.MainScreenGuest.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.thuctaptotnghiep.doantttn.Constant
import com.thuctaptotnghiep.doantttn.R
import com.thuctaptotnghiep.doantttn.adapter.CartAdapter
import com.thuctaptotnghiep.doantttn.databinding.FragmentCartBinding
import com.thuctaptotnghiep.doantttn.db.model.Cart
import com.thuctaptotnghiep.doantttn.dialog.ConfirmPayDialog
import com.thuctaptotnghiep.doantttn.ui.MainScreenGuest.MainGuestActivity
import com.thuctaptotnghiep.doantttn.ui.MainScreenGuest.MainGuestViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CartFragment : Fragment() {

    lateinit var binding: FragmentCartBinding
    lateinit var viewModel: MainGuestViewModel
    lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_cart, container, false)
        viewModel = (activity as MainGuestActivity).viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpCartAdapter()
        intialViewModel()
        setOnClickCheckOutBtn()
    }

    fun intialViewModel() {
        val prefs = requireActivity().getSharedPreferences(
            Constant.SHARE_PREFERENCE_NAME,
            Context.MODE_PRIVATE
        )
        val email = prefs.getString("email", "null")
        if (email == "null") {
            Snackbar.make(binding.root, "email NULL", Snackbar.LENGTH_LONG)
                .setBackgroundTint(
                    Color.RED
                ).show()
        }
        viewModel.listCart.observe(viewLifecycleOwner, {
            cartAdapter.diff.submitList(it)
        })
        CoroutineScope(Dispatchers.Default).launch {
            viewModel.getListCart(email!!)
        }
    }

    fun setUpCartAdapter() {
        val prefs = requireActivity().getSharedPreferences(
            Constant.SHARE_PREFERENCE_NAME,
            Context.MODE_PRIVATE
        )
        val email = prefs.getString("email", "null")
        if (email == "null") {
            Snackbar.make(binding.root, "email NULL", Snackbar.LENGTH_LONG)
                .setBackgroundTint(
                    Color.RED
                ).show()
        }
        cartAdapter = CartAdapter()
        binding.listCartRecycleView.layoutManager = LinearLayoutManager(context)
        binding.listCartRecycleView.adapter = cartAdapter
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val cartDel = cartAdapter.diff.currentList[position]
                CoroutineScope(Dispatchers.Default).launch {
                    viewModel.deleteCart(cartDel, email!!)
                    Snackbar.make(binding.root, "Delete Cart Successful ", Snackbar.LENGTH_SHORT)
                        .apply {
                            setAction("Undo") {

                            }
                        }.show()
                }
            }
        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.listCartRecycleView)

        cartAdapter.setOnClickMinusCallBack {
            setOnClickMinusCallBack(it)
        }

        cartAdapter.setOnClickPlusCallBack {
            setOnClickPlusCallBack(it)
        }
    }

    fun setOnClickMinusCallBack(cart: Cart) {
        val prefs = requireActivity().getSharedPreferences(
            Constant.SHARE_PREFERENCE_NAME,
            Context.MODE_PRIVATE
        )
        val email = prefs.getString("email", "null")
        if (email == "null") {
            Snackbar.make(binding.root, "email NULL", Snackbar.LENGTH_LONG)
                .setBackgroundTint(
                    Color.RED
                ).show()
        }
        CoroutineScope(Dispatchers.Default).launch {
            viewModel.updateCartWhenMinus(cart, email!!)
        }
    }

    fun setOnClickPlusCallBack(cart: Cart) {
        val prefs = requireActivity().getSharedPreferences(
            Constant.SHARE_PREFERENCE_NAME,
            Context.MODE_PRIVATE
        )
        val email = prefs.getString("email", "null")
        if (email == "null") {
            Snackbar.make(binding.root, "email NULL", Snackbar.LENGTH_LONG)
                .setBackgroundTint(
                    Color.RED
                ).show()
        }
        CoroutineScope(Dispatchers.Default).launch {
            viewModel.updateCartWhenPlus(cart, email!!)
        }
    }


    fun setOnClickCheckOutBtn() {
        binding.checkOutBtn.setOnClickListener {
            val openConfirmDialog = ConfirmPayDialog(viewLifecycleOwner)
            openConfirmDialog.show(requireActivity().supportFragmentManager, "confirm cart dialog")
        }
    }
}