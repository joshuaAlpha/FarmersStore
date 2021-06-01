package com.example.farmersstore.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.farmersstore.R
import com.example.farmersstore.firebase.FireStoreAuthClass
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){

            window.insetsController?.hide(WindowInsets.Type.statusBars())

        }
        else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        //Set Onclick Listener of the register button

        tv_register.setOnClickListener {
            intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

        //Setting OnclickListener on Login Button.
        btn_login.setOnClickListener {
            if (validateLogin()){
                showProgressDialog("Logging in")
                val email = findViewById<TextView>(R.id.et_email_id).text.toString().trim()
                val password = findViewById<TextView>(R.id.et_password).text.toString().trim()
                FireStoreAuthClass().loginUser(this, email, password)
            }
        }

        // Setting onClickListener on forgot password text

        tv_forgot_password.setOnClickListener {
            intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }


    }

    private  fun validateLogin() : Boolean {
        val email = findViewById<TextView>(R.id.et_email_id).text.toString().trim()
        val password = findViewById<TextView>(R.id.et_password).text.toString().trim()
        return when {
            TextUtils.isEmpty(email) ->{
                showSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }

            TextUtils.isEmpty(password) -> {
                showSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }

            // Check for valid email
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                showSnackBar(resources.getString(R.string.err_msg_enter_valid_email), true)
                false
            }

            else -> true
        }
    }

    fun loginSuccess() {

        intent = Intent(this@LoginActivity, DashboardActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}