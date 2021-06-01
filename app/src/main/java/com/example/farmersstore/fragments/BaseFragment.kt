package com.example.farmersstore.fragments

import android.app.Dialog
import androidx.fragment.app.Fragment
import com.example.farmersstore.R
import kotlinx.android.synthetic.main.activity_progress_indicator.*

open class BaseFragment : Fragment() {
    private lateinit var mProgressDialog: Dialog

    fun  showProgressDialog(text: String){
        mProgressDialog = Dialog(requireActivity())

        mProgressDialog.setContentView(R.layout.activity_progress_indicator)

        mProgressDialog.tv_progress_text.text = text

        mProgressDialog.setCancelable(false)

        mProgressDialog.show()
    }

    fun hideProgressDialog(){
        mProgressDialog.dismiss()
    }
}