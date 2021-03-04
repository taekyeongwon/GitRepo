package com.emgram.kr.tmaas.myapp.core.custom.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.Spinner
import androidx.appcompat.widget.AppCompatSpinner

class TestSpinner
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0)
    : AppCompatSpinner(context, attrs, defStyle) {
    private var isClicked = false

    override fun performClick(): Boolean {
        Log.d("TestSpinner", "performClick")
        return super.performClick()
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {    //true: dropdown close / false: dropdown open
        super.onWindowFocusChanged(hasWindowFocus)
        Log.d("TestSpinner", "${hasWindowFocus}")
    }

}