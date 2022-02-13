package com.caffeine.ecommercefirebase.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.caffeine.ecommercefirebase.R
import com.caffeine.ecommercefirebase.databinding.FragmentDetailsBinding
import com.caffeine.ecommercefirebase.helper.AlertDialog
import com.caffeine.ecommercefirebase.services.model.CartModel
import com.caffeine.ecommercefirebase.util.Constants
import com.caffeine.ecommercefirebase.util.DataState
import com.caffeine.ecommercefirebase.util.OnColorClick
import com.caffeine.ecommercefirebase.util.OnSizeClick
import com.caffeine.ecommercefirebase.view.activities.DashboardActivity
import com.caffeine.ecommercefirebase.view.adapter.ColorAdapter
import com.caffeine.ecommercefirebase.view.adapter.ImageAdapter
import com.caffeine.ecommercefirebase.view.adapter.SizeAdapter
import com.caffeine.ecommercefirebase.viewmodel.ProductViewModel
import com.squareup.picasso.Picasso

class DetailsFragment() : Fragment(), OnSizeClick, OnColorClick {

    private lateinit var binding: FragmentDetailsBinding
    private val productViewModel: ProductViewModel by viewModels()
    private val product by navArgs<DetailsFragmentArgs>()

    private var size : String? = null
    private var color : String? = null
    private var quantity = 1
    private var mainPrice : Int = 0
    private var currentPrice : Int = 0

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater)

        Picasso.get().load(product.product.images?.get(0)?.url).into(binding.productImage)
        binding.productName.text = product.product.name
        binding.productDescription.text = product.product.desc
        binding.productPrice.text = "$" + product.product.price

        size = product.product.sizes!![0].size
        color = product.product.colors!![0].color

        binding.imageRecyclerView.layoutManager = Constants.getHorizontalLayout(requireContext())
        binding.sizeRecyclerView.layoutManager = Constants.getHorizontalLayout(requireContext())
        binding.colorRecyclerView.layoutManager = Constants.getHorizontalLayout(requireContext())

        val imageAdapter = ImageAdapter(product.product.images!!, binding.productImage)
        val sizeAdapter = SizeAdapter(product.product.sizes!!, requireContext(), this)
        val colorAdapter = ColorAdapter(product.product.colors!!, requireContext(), this)
        binding.imageRecyclerView.adapter = imageAdapter
        binding.sizeRecyclerView.adapter = sizeAdapter
        binding.colorRecyclerView.adapter = colorAdapter

        mainPrice = product.product.price!!.toInt()
        currentPrice = product.product.price!!.toInt()

        binding.plus.setOnClickListener{
            if (quantity < 5){
                quantity++
                currentPrice = mainPrice * quantity
                binding.productPrice.text = "$$currentPrice"
                binding.quantity.text = quantity.toString()
            }
        }

        binding.minus.setOnClickListener{
            if (quantity != 1){
                quantity--
                currentPrice -= mainPrice
                binding.productPrice.text = "$$currentPrice"
                binding.quantity.text = quantity.toString()
            }
        }

        binding.btnAddToCart.setOnClickListener{
            val cart = CartModel(
                product.product.id,
                product.product.name,
                currentPrice.toString(),
                product.product.category,
                binding.quantity.text.toString(),
                color,
                size
            )
            productViewModel.addToCart(cart)
        }

        productViewModel.carts.observe(viewLifecycleOwner) { state ->
            when(state){
                is DataState.Loading -> {
                    binding.textAddToCart.visibility = View.INVISIBLE
                    binding.progressBar.visibility = View.VISIBLE
                }

                is DataState.Success -> {
                    Toast.makeText(requireContext(), "Product added to your cart", Toast.LENGTH_LONG).show()
                    binding.textAddToCart.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    Navigation.findNavController(binding.root).navigate(R.id.details_to_cart)
                }

                is DataState.Failed -> {
                    AlertDialog.getInstance(requireContext()).showAlertDialog(state.message!!, "Close")
                }
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as DashboardActivity).setActionBarTitle("Product Details")
    }

    override fun onSizeClick(value: String) {
        size = value
    }

    override fun onColorClick(value: String) {
        color = value
    }
}