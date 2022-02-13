package com.caffeine.ecommercefirebase.view.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.caffeine.ecommercefirebase.R
import com.caffeine.ecommercefirebase.databinding.ActivityDashboardBinding
import com.caffeine.ecommercefirebase.services.model.Colors
import com.caffeine.ecommercefirebase.services.model.Images
import com.caffeine.ecommercefirebase.services.model.ProductDetails
import com.caffeine.ecommercefirebase.services.model.Sizes
import com.caffeine.ecommercefirebase.util.Constants

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDashboardBinding
    private var count = 0

    private lateinit var navController: NavController

    @SuppressLint("RtlHardcoded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.fragmentContainerView2)
        NavigationUI.setupWithNavController(binding.navView, navController)

        /*
        val variants = ArrayList<Sizes>()
        variants.add(Sizes("S"))
        variants.add(Sizes("XL"))
        variants.add(Sizes("M"))
        variants.add(Sizes("L"))
        val colors = ArrayList<Colors>()
        colors.add(Colors("Blue"))
        colors.add(Colors("Red"))
        colors.add(Colors("White"))
        colors.add(Colors("Black"))
        val images = ArrayList<Images>()
        images.add(Images("https://static-01.daraz.com.bd/p/f4032e107d55a0373973cc19cb7baa29.jpg"))
        images.add(Images("https://static-01.daraz.com.bd/p/0e554c431bfb49b1857e32b1fc67bd55.jpg"))
        val id = System.currentTimeMillis().toString()
        val cat = "Winter"
        val product = ProductDetails(
            id,
            "Name",
            "500",
            cat,
            variants,
            colors,
            images,
            "210",
            "1000",
            "Product description",
            "314")

        Constants.reference.child("Products").child(cat).child(id).setValue(product)
         */

        binding.backBtn.setOnClickListener{
            if (binding.pageTitle.text.toString() != "Home"){
                onBackPressed()
            }
            else{
                binding.drawer.openDrawer(Gravity.LEFT)
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