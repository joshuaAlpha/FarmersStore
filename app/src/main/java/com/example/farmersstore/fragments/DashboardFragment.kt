package com.example.farmersstore.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.recyclerview.widget.GridLayoutManager
import com.example.farmersstore.R
import com.example.farmersstore.activities.ProductDetailsActivity
import com.example.farmersstore.activities.SettingsActivity
import com.example.farmersstore.adaptors.DashboardItemsAdaptor
import com.example.farmersstore.firebase.FireStoreProductClass
import com.example.farmersstore.models.Product
import com.example.farmersstore.utils.Constants
import kotlinx.android.synthetic.main.activity_dashboard_fragment.*

class DashboardFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_dashboard_fragment, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dashboard_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(activity, SettingsActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onResume() {
        super.onResume()
        fetchProducts()
    }

    private fun fetchProducts() {
        showProgressDialog(resources.getString(R.string.please_wait))
        FireStoreProductClass().fetchProducts(this)
    }

    fun fetchProductsSuccess(products : ArrayList<Product>) {
        hideProgressDialog()
        if (products.isNotEmpty()) {
            rv_dashboard_items.visibility = View.VISIBLE
            tv_no_dashboard_items_found.visibility = View.INVISIBLE
            rv_dashboard_items.layoutManager = GridLayoutManager(activity, 2)
            rv_dashboard_items.setHasFixedSize(true)
            val adaptorProducts = DashboardItemsAdaptor(requireActivity(), products, this)
            rv_dashboard_items.adapter = adaptorProducts
        } else {
            rv_dashboard_items.visibility = View.GONE
            tv_no_dashboard_items_found.visibility = View.VISIBLE
        }
    }
    fun fetchProductsFailure(message : String) {
        hideProgressDialog()
        Log.d("Fetch Products Error", message)
    }

    fun goToProductDetails(product: Product) {
        val intent = Intent(requireActivity(), ProductDetailsActivity::class.java)
        intent.putExtra(Constants.PRODUCT_ID, product.id)
        intent.putExtra(Constants.PRODUCT_USER_ID, product.userId)
        startActivity(intent)
    }
}
