package com.example.farmersstore.adaptors

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.farmersstore.R
import com.example.farmersstore.fragments.ProductsFragment
import com.example.farmersstore.models.Product
import com.example.farmersstore.utils.GlideLoader
import kotlinx.android.synthetic.main.activity_itemlist_lyout.view.*

class UserProductsAdaptor (
    private val context: Context,
    private val products : ArrayList<Product>,
    private val fragment: ProductsFragment,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DashboardItemsAdaptor.MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.activity_itemlist_lyout, parent, false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val product = products[position]
        if (holder is DashboardItemsAdaptor.MyViewHolder) {
            GlideLoader(context).loadProductImage(product.imageUrl, holder.itemView.iv_item_image)
            holder.itemView.tv_item_name.text = product.title
            holder.itemView.tv_item_price.text = "${com.example.farmersstore.utils.Constants.DOLLAR_SIGN} ${product.price}"
            holder.itemView.ib_delete_product.setOnClickListener {
                fragment.deleteProductConfirmation(product.id)
            }
            holder.itemView.item_list_card.setOnClickListener {
                fragment.goToProductDetails(product)
            }
        }
    }
    override fun getItemCount(): Int {
        return products.size
    }

}