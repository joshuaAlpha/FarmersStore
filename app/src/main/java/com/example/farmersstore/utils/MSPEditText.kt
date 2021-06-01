package com.example.farmersstore.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class MSPEditText(context: Context, attrs: AttributeSet):AppCompatEditText(context, attrs) {

    init {

        applyFont()

    }

    private fun applyFont(){
        //This is used to get the file from the asset folder and set to title textview
        val typeface: Typeface =
            Typeface.createFromAsset(context.assets, "Montserrat-Bold.ttf")
        setTypeface(typeface)

    }
}