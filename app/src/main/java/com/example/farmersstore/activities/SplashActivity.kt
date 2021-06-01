package com.example.farmersstore.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowInsets
import android.view.WindowManager
import com.example.farmersstore.R
import com.example.farmersstore.firebase.FireStoreAuthClass
import com.example.farmersstore.models.User
import com.example.farmersstore.utils.Constants

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        FireStoreAuthClass().getCurrentUser(this)

    }

    fun loadHomeScreen() {
        intent = Intent(this@SplashActivity, DashboardActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun loadLoginScreen() {
        intent = Intent(this@SplashActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun loadProfileScreen(user: User) {
        intent = Intent(this@SplashActivity, ProfileActivity::class.java)
        intent.putExtra(Constants.EXTRA_USER_DETAILS, user)
        startActivity(intent)
        finish()
    }

}