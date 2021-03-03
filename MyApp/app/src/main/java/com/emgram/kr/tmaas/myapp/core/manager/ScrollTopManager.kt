package com.emgram.kr.tmaas.myapp.core.manager

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

open class ScrollTopManager: RecyclerView.OnScrollListener() {  //register, unregister 호출 필요
    private var listener: OnScrollTopListener? = null

    fun setScrollTopListener(listener: OnScrollTopListener) {
        this.listener = listener
    }

    fun register(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(this)
    }

    fun unregister(recyclerView: RecyclerView) {
        recyclerView.removeOnScrollListener(this)
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if(newState == RecyclerView.SCROLL_STATE_IDLE) {
            val manager = recyclerView.layoutManager
            if(manager is LinearLayoutManager) {
                listener?.onVisibleTopButton(manager.findFirstVisibleItemPosition() != 0)
            }
        }
    }

    interface OnScrollTopListener {
        fun onVisibleTopButton(isVisible: Boolean)
    }
}