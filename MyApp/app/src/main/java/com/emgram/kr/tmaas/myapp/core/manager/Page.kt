package com.emgram.kr.tmaas.myapp.core.manager


class Page(val page: Int, val total: Int): PageItem {
    override fun getCurrentPage(): Int {
        return page
    }

    override fun getTotalPage(): Int {
        val value = total / getPageCnt()
        val remain = total % getPageCnt()

        return if (remain > 0) { value + 1 } else { value }
    }

    override fun getTotalCnt(): Int {
        return total
    }

    override fun getPageCnt(): Int {
        return 10
    }
}