package com.emgram.kr.tmaas.myapp.core.manager

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager

/**
 * RecyclerView 페이징 처리 관리 매니저
 */
class PagingManager(recyclerView: RecyclerView) : ScrollTopManager() {

    private var layoutManager: LinearLayoutManager? = null
    private var pageItem: PageItem? = null

    private var callback: PagerCallback? = null

    init {
        layoutManager = recyclerView.layoutManager as? LinearLayoutManager
    }

    fun setCurrentPage(pageItem: PageItem?) {   //api 응답 왔을 때 현재 page 세팅
        this.pageItem = pageItem
    }

    fun setCallback(callback: PagerCallback) {  //로딩 여부, 다음 페이지 로딩 콜백 세팅
        this.callback = callback
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val lastVisibleItemPosition = layoutManager?.findLastCompletelyVisibleItemPosition() ?: -1
        val itemTotalCount = recyclerView.adapter?.itemCount ?: 0

        if (callback?.isLoading() == false && !isLastPage()) {      //api 프로그레스바 안돌고 마지막 페이지가 아니면
            if(lastVisibleItemPosition == itemTotalCount - 1            //마지막에 보이는 아이템의 인덱스가 아이템 전체 개수고
                && pageItem?.getTotalPage()?:0 > pageItem?.getCurrentPage()?:0      //현재 페이지가 전체 페이지보다 작은 경우에
            ) {
                callback?.loadMoreItems(pageItem?.getCurrentPage() ?: 0)    //loadMoreItems 호출
            }
        }
    }

    private fun isLastPage() : Boolean {
        return pageItem?.let {it.getTotalPage() == it.getCurrentPage()} ?: false
    }

    interface PagerCallback {
        fun isLoading() : Boolean
        fun loadMoreItems(currentPage: Int)
    }

}