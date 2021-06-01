package com.example.farmersstore.adaptors

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.farmersstore.R
import com.example.farmersstore.fragments.DashboardFragment
import com.example.farmersstore.models.Product
import com.example.farmersstore.utils.GlideLoader
import com.google.android.gms.common.internal.Constants
import kotlinx.android.synthetic.main.activity_item_dashboard_layout.view.*

class DashboardItemsAdaptor (
    private val context: Context,
    private val  products: ArrayList<Product>,
    private val fragment: DashboardFragment,

    ) :

        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.activity_dashboard,
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val product = products[position]
        if (holder is MyViewHolder) {
            GlideLoader(context).loadProductImage(product.imageUrl, holder.itemView.iv_dashboard_item_image)
            holder.itemView.tv_dashboard_item_price.text = product.title
            holder.itemView.tv_dashboard_item_price.text = "${com.example.farmersstore.utils.Constants.DOLLAR_SIGN} ${product.price}"
            holder.itemView.dashboard_item_card.setOnClickListener{
                fragment.goToProductDetails(product)
            }

        }
    }

    override fun getItemCount(): Int {
        return products.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)

        }
