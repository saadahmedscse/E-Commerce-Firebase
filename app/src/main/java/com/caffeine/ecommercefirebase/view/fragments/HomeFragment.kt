package com.caffeine.ecommercefirebase.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.caffeine.ecommercefirebase.R
import com.caffeine.ecommercefirebase.databinding.FragmentHomeBinding
import com.caffeine.ecommercefirebase.services.model.ProductDetails
import com.caffeine.ecommercefirebase.util.Task
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

        val categoryLayoutManager = getLayoutManager()
        val popularLayoutManager = getLayoutManager()
        val menLayoutManager = getLayoutManager()
        val womenLayoutManager = getLayoutManager()
        val childLayoutManager = getLayoutManager()

        binding.categoryRecyclerView.layoutManager = categoryLayoutManager
        binding.popularRecyclerView.layoutManager = popularLayoutManager
        binding.menRecyclerView.layoutManager = menLayoutManager
        binding.womenRecyclerView.layoutManager = womenLayoutManager
        binding.childRecyclerView.layoutManager = childLayoutManager

        productDetails = ArrayList()
        ForMen = ArrayList()
        ForWomen = ArrayList()
        ForChild = ArrayList()
        Popular = ArrayList()

        productViewModel.getCategories()
        productViewModel.categoryLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Task.Loading -> {
                    //loading
                }

                is Task.Success -> {
                    //success
                    val categoryAdapter = CategoryAdapter(it.data as ArrayList<String>)
                    binding.categoryRecyclerView.adapter = categoryAdapter
                }

                is Task.Failed -> {
                    //failed
                }
            }
        })

        productViewModel.getAllProducts()
        productViewModel.allProducts.observe(viewLifecycleOwner) {
            when (it) {
                is Task.Loading -> {}

                is Task.Success -> {
                    productDetails = it.data as ArrayList<ProductDetails>
                    setAdapters()
                }

                is Task.Failed -> {}
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        (activity as DashboardActivity).setActionBarTitle("Home")
    }

    private fun getLayoutManager() : LinearLayoutManager{
        return LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setAdapters() {
        for (obj in productDetails){
            if (obj.category == "For Men"){
                ForMen.add(obj)
            }

            if (obj.category == "For Women"){
                ForWomen.add(obj)
            }

            if (obj.category == "For Child"){
                ForChild.add(obj)
            }

            if (obj.category == "Summer"){
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