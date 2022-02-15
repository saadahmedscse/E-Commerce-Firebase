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

        productViewModel.getMenProducts("For Men")
        productViewModel.getWomenProducts("For Women")
        productViewModel.getChildProducts("For Child")

        productViewModel.menProductMutableLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Loading -> {}

                is DataState.Success -> {
                    val adapter = ProductAdapter(requireContext(), it.data as ArrayList<ProductDetails>)
                    binding.menRecyclerView.adapter = adapter
                    binding.popularRecyclerView.adapter = adapter

                    binding.shimmerPopular.root.visibility = View.GONE
                    binding.shimmerMen.root.visibility = View.GONE
                    //binding.shimmerWomen.root.visibility = View.GONE
                    //binding.shimmerChild.root.visibility = View.GONE
                }

                is DataState.Failed -> {
                    AlertDialog.getInstance(requireContext()).showAlertDialog(it.message!!, "Close")
                }
            }
        }

        productViewModel.womenProductMutableLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Loading -> {}

                is DataState.Success -> {
                    val adapter = ProductAdapter(requireContext(), it.data as ArrayList<ProductDetails>)
                    binding.womenRecyclerView.adapter = adapter

                    //binding.shimmerPopular.root.visibility = View.GONE
                    //binding.shimmerMen.root.visibility = View.GONE
                    binding.shimmerWomen.root.visibility = View.GONE
                    //binding.shimmerChild.root.visibility = View.GONE
                }

                is DataState.Failed -> {
                    AlertDialog.getInstance(requireContext()).showAlertDialog(it.message!!, "Close")
                }
            }
        }

        productViewModel.childProductMutableLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Loading -> {}

                is DataState.Success -> {
                    val adapter = ProductAdapter(requireContext(), it.data as ArrayList<ProductDetails>)
                    binding.childRecyclerView.adapter = adapter

                    //binding.shimmerPopular.root.visibility = View.GONE
                    //binding.shimmerMen.root.visibility = View.GONE
                    //binding.shimmerWomen.root.visibility = View.GONE
                    binding.shimmerChild.root.visibility = View.GONE
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

        (activity as DashboardActivity).setActionBarTitle("Home")
    }
}