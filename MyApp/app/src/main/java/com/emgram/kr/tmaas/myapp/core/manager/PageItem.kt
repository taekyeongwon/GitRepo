package com.emgram.kr.tmaas.myapp.core.manager

interface PageItem {
    fun getCurrentPage(): Int
    fun getTotalCnt(): Int
    fun getTotalPage(): Int
    fun getPageCnt(): Int
}