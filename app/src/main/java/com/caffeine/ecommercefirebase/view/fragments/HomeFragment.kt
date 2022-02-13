package com.caffeine.ecommercefirebase.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.caffeine.ecommercefirebase.databinding.FragmentHomeBinding
import com.caffeine.ecommercefirebase.helper.AlertDialog
import com.caffeine.ecommercefirebase.services.model.ProductDetails
import com.caffeine.ecommercefirebase.util.Constants
import com.caffeine.ecommercefirebase.util.DataState
import com.caffeine.ecommercefirebase.view.activities.DashboardActivity
import com.caffeine.ecommercefirebase.view.adapter.CategoryAdapter
import com.caffeine.ecommercefirebase.view.adapter.ProductAdapter
import com.caffeine.ecommercefirebase.viewmodel.ProductViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val productViewModel: ProductViewModel by viewModels()
    private lateinit var productDetails: ArrayList<ProductDetails>
    private lateinit var ForMen: ArrayList<ProductDetails>
    private lateinit var ForWomen: ArrayList<ProductDetails>
    private lateinit var ForChild: ArrayList<ProductDetails>
    private lateinit var Popular: ArrayList<ProductDetails>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.categoryRecyclerView.layoutManager = Constants.getHorizontalLayout(requireContext())
        binding.popularRecyclerView.layoutManager = Constants.getHorizontalLayout(requireContext())
        binding.menRecyclerView.layoutManager = Constants.getHorizontalLayout(requireContext())
        binding.womenRecyclerView.layoutManager = Constants.getHorizontalLayout(requireContext())
        binding.childRecyclerView.layoutManager = Constants.getHorizontalLayout(requireContext())

        productDetails = ArrayList()
        ForMen = ArrayList()
        ForWomen = ArrayList()
        ForChild = ArrayList()
        Popular = ArrayList()

        productViewModel.getCategories()
        productViewModel.categoryLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is DataState.Loading -> {}

                is DataState.Success -> {
                    val categoryAdapter = CategoryAdapter(state.data as ArrayList<String>)
                    binding.categoryRecyclerView.adapter = categoryAdapter

                    binding.shimmerCategory.root.visibility = View.GONE
                }

                is DataState.Failed -> {
                    AlertDialog.getInstance(requireContext()).showAlertDialog(state.message!!, "Close")
                }
            }
        }

        productViewModel.getAllProducts()
        productViewModel.allProducts.observe(viewLifecycleOwner) { state ->
            when (state) {
                is DataState.Loading -> {}

                is DataState.Success -> {
                    productDetails = state.data as ArrayList<ProductDetails>
                    setAdapters()

                    binding.shimmerPopular.root.visibility = View.GONE
                    binding.shimmerMen.root.visibility = View.GONE
                    binding.shimmerWomen.root.visibility = View.GONE
                    binding.shimmerChild.root.visibility = View.GONE
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

        (activity as DashboardActivity).setActionBarTitle("Home")
    }

    private fun setAdapters() {
        for (obj in productDetails) {
            if (obj.category == "For Men") {
                ForMen.add(obj)
            }

            if (obj.category == "For Women") {
                ForWomen.add(obj)
            }

            if (obj.category == "For Child") {
                ForChild.add(obj)
            }

            if (obj.category == "Summer") {
                Popular.add(obj)
            }
        }

        val menAdapter = ProductAdapter(requireContext(), ForMen)
        binding.menRecyclerView.adapter = menAdapter

        val womenAdapter = ProductAdapter(requireContext(), ForWomen)
        binding.womenRecyclerView.adapter = womenAdapter

        val childAdapter = ProductAdapter(requireContext(), ForChild)
        binding.childRecyclerView.adapter = childAdapter

        val popularAdapter = ProductAdapter(requireContext(), Popular)
        binding.popularRecyclerView.adapter = popularAdapter
    }
}