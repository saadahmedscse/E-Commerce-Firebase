package com.caffeine.ecommercefirebase.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.caffeine.ecommercefirebase.R
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val user = FirebaseAuth.getInstance().currentUser

        Handler(Looper.getMainLooper()).postDelayed(Runnable {

            if (user != null){
                startActivity(Intent(this, DashboardActivity::class.java))
                finish()
            }
            else{
                startActivity(Intent(this, AuthenticationActivity::class.java))
                finish()
            }
        }, 0)
    }
}