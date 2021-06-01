package com.example.farmersstore.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.farmersstore.R

class OrdersFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.activity_orders_fragment, container, false)

        val textView: TextView = root.findViewById(R.id.text_orders)
        textView.text = resources.getString(R.string.title_orders)
        return root
    }
}