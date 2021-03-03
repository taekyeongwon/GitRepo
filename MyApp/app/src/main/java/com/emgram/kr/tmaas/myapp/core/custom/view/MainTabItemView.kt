package com.emgram.kr.tmaas.myapp.core.custom.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.emgram.kr.tmaas.myapp.R
import kotlinx.android.synthetic.main.ui_main_tab_item.view.*


class MainTabItemView
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(getContext()).inflate(R.layout.ui_main_tab_item, this)
    }

    fun setName(name : String) {
        name_tv.text = name
    }

    fun setIcon(@DrawableRes icon : Int) {
        ic_iv.setImageResource(icon)
    }

    fun setEnable(isEnable : Boolean) {
        ic_iv.isEnabled = isEnabled
    }
}