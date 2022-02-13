package com.caffeine.ecommercefirebase.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.caffeine.ecommercefirebase.R

class AuthenticationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
    }

    override fun onBackPressed() {
        var count : Int = supportFragmentManager.backStackEntryCount

        if (count == 0){
            super.onBackPressed()
        }
        else{
            supportFragmentManager.popBackStack()
        }
    }
}