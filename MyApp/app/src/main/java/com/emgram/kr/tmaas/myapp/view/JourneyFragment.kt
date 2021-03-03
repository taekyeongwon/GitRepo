package com.emgram.kr.tmaas.myapp.view

import com.emgram.kr.tmaas.myapp.R
import com.emgram.kr.tmaas.myapp.base.BaseFragment
import com.emgram.kr.tmaas.myapp.viewmodel.MainViewModel

class JourneyFragment: BaseFragment<MainViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_journey
    override lateinit var viewModel: MainViewModel

    override fun initView() {

    }

    override fun initObserver() {

    }

    override fun initListener() {

    }
}