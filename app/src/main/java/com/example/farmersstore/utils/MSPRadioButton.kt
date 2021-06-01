package com.example.farmersstore.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatRadioButton

class MSPRadioButton(context: Context, attrs: AttributeSet):AppCompatRadioButton(context, attrs) {

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