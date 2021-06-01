package com.example.farmersstore.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.VISIBLE
import com.example.farmersstore.R
import com.example.farmersstore.firebase.FireStoreAuthClass
import com.example.farmersstore.firebase.FireStoreProductClass
import com.example.farmersstore.models.Product
import com.example.farmersstore.utils.Constants
import com.example.farmersstore.utils.GlideLoader
import kotlinx.android.synthetic.main.activity_product_details.*

class ProductDetailsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        setupActionBar()

        if (intent.hasExtra(Constants.PRODUCT_ID)) {
            val productId = intent.getStringExtra(Constants.PRODUCT_ID)!!
            val userId = intent.getStringExtra(Constants.PRODUCT_USER_ID)!!
            fetchProduct(productId)
            verifyProductOwnership(userId)
        }
    }

    private fun setupActionBar() {
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_product_details_activity)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun fetchProduct(productId : String) {
        showProgressDialog(resources.getString(R.string.please_wait))
        FireStoreProductClass().fetchProduct(this, productId)
    }
    private fun verifyProductOwnership(productUserId : String) {
        val currentUserId = FireStoreAuthClass().getCurrentUserId()
        if (productUserId != currentUserId) {
            btn_add_to_cart.visibility = VISIBLE
        }
    }

    @SuppressLint("SetTextI18n")
    fun fetchProductSuccess(product : Product) {
        hideProgressDialog()
        GlideLoader(this@ProductDetailsActivity).loadProductImage(
            product.imageUrl,
            iv_product_detail_image
        )
        tv_product_details_title.text = product.title
        tv_product_details_price.text = "${Constants.DOLLAR_SIGN} ${product.price}"
        tv_product_details_description.text = product.description
        tv_product_details_stock_quantity.text = product.quantity.toString()
    }

    fun fetchProductFailure(errorMessage : String) {
        hideProgressDialog()
        showSnackBar(errorMessage, false)
    }

}