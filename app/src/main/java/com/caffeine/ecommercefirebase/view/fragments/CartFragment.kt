package com.caffeine.ecommercefirebase.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.caffeine.ecommercefirebase.R
import com.caffeine.ecommercefirebase.databinding.FragmentCartBinding
import com.caffeine.ecommercefirebase.helper.AlertDialog
import com.caffeine.ecommercefirebase.services.model.CartModel
import com.caffeine.ecommercefirebase.util.Constants
import com.caffeine.ecommercefirebase.util.DataState
import com.caffeine.ecommercefirebase.view.activities.DashboardActivity
import com.caffeine.ecommercefirebase.view.adapter.CartAdapter
import com.caffeine.ecommercefirebase.viewmodel.CartViewModel

class CartFragment : Fragment() {

    private lateinit var binding : FragmentCartBinding
    private val cartViewModel : CartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater)

        binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        cartViewModel.getCartItems()
        cartViewModel.getCarts.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Loading -> {
                    //loading
                }

                is DataState.Success -> {
                    //success

                    val adapter = CartAdapter(
                        requireContext(),
                        it.data as ArrayList<CartModel>,
                        binding.subTotal,
                        binding.totalPrice,
                        viewLifecycleOwner,
                        cartViewModel,
                        binding.checkoutBtn,
                        binding.btnText,
                        binding.progressBar)

                    if (adapter.itemCount != 0){
                        binding.emptyCart.visibility = View.GONE
                        binding.rootLayout.visibility = View.VISIBLE
                    }
                    else{
                        binding.rootLayout.visibility = View.GONE
                        binding.emptyCart.visibility = View.VISIBLE
                    }

                    binding.cartRecyclerView.adapter = adapter
                }

                is DataState.Failed -> {
                    AlertDialog.getInstance(requireContext()).showAlertDialog(it.message!!, "Close")
                }
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as DashboardActivity).setActionBarTitle("My Carts")
    }
}