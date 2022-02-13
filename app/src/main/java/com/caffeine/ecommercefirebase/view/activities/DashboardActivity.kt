package com.caffeine.ecommercefirebase.view.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.caffeine.ecommercefirebase.R
import com.caffeine.ecommercefirebase.databinding.ActivityDashboardBinding
import com.caffeine.ecommercefirebase.view.fragments.ProfileFragment
import com.google.android.material.navigation.NavigationView

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