package com.caffeine.ecommercefirebase.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.caffeine.ecommercefirebase.R
import com.caffeine.ecommercefirebase.databinding.FragmentHomeBinding
import com.caffeine.ecommercefirebase.util.Task
import com.caffeine.ecommercefirebase.view.activities.DashboardActivity
import com.caffeine.ecommercefirebase.view.adapter.CategoryAdapter
import com.caffeine.ecommercefirebase.viewmodel.ProductViewModel

class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private val productViewModel : ProductViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val categoryLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.categoryRecyclerView.layoutManager = categoryLayoutManager

        productViewModel.getCategories()
        productViewModel.categoryLiveData.observe(viewLifecycleOwner, Observer {
            when (it){
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

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        (activity as DashboardActivity).setActionBarTitle("Home")
    }
}