package com.caffeine.ecommercefirebase.view.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.caffeine.ecommercefirebase.R
import com.caffeine.ecommercefirebase.databinding.ActivityDashboardBinding
import com.caffeine.ecommercefirebase.services.model.Colors
import com.caffeine.ecommercefirebase.services.model.Images
import com.caffeine.ecommercefirebase.services.model.ProductDetails
import com.caffeine.ecommercefirebase.services.model.Sizes
import com.caffeine.ecommercefirebase.util.Constants
import com.caffeine.ecommercefirebase.util.DataState
import com.caffeine.ecommercefirebase.view.fragments.CartFragment
import com.caffeine.ecommercefirebase.view.fragments.HomeFragment
import com.caffeine.ecommercefirebase.viewmodel.CartViewModel

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDashboardBinding
    private val cartViewModel : CartViewModel by viewModels()
    private var count = 0

    private lateinit var navController: NavController

    @SuppressLint("RtlHardcoded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.fragmentContainerView2)
        NavigationUI.setupWithNavController(binding.navView, navController)

        binding.backBtn.setOnClickListener{
            if (binding.pageTitle.text.toString() != "Home"){
                onBackPressed()
            }
            else{
                binding.drawer.openDrawer(Gravity.LEFT)
            }
        }

        binding.btnCart.setOnClickListener{
            when (navController.currentDestination?.id){
                R.id.homeFragment -> navController.navigate(R.id.home_to_cart)
                R.id.detailsFragment -> navController.navigate(R.id.details_to_cart)
                R.id.profileFragment -> navController.navigate(R.id.profile_to_cart)
            }
        }

        cartViewModel.getCartItems()
        cartViewModel.getCarts.observe(this){
            when (it){
                is DataState.Success -> {
                    if (it.data?.size != 0) binding.cartNotification.visibility = View.VISIBLE
                    else binding.cartNotification.visibility = View.GONE
                }
            }
        }
    }

    fun setActionBarTitle(title : String){
        binding.pageTitle.text = title
        if (title != "Home"){
            binding.backBtn.setImageResource(R.drawable.arrow)
        }
        else{
            binding.backBtn.setImageResource(R.drawable.logo)
        }
    }

    override fun onBackPressed() {
        count = supportFragmentManager.backStackEntryCount

        if (count == 0){
            super.onBackPressed()
        }
        else{
            supportFragmentManager.popBackStack()
        }
    }
}