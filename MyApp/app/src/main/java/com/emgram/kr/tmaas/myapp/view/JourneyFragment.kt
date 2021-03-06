package com.emgram.kr.tmaas.myapp.view

import androidx.lifecycle.ViewModelProvider
import com.emgram.kr.tmaas.myapp.R
import com.emgram.kr.tmaas.myapp.base.BaseFragment
import com.emgram.kr.tmaas.myapp.core.util.TMProviderFactory
import com.emgram.kr.tmaas.myapp.viewmodel.MainViewModel
import java.lang.Exception

class JourneyFragment: BaseFragment<MainViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_journey
    override lateinit var viewModel: MainViewModel

    override fun initView() {
        try {
            viewModel = ViewModelProvider(requireActivity(), TMProviderFactory()).get(MainViewModel::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun initObserver() {

    }

    override fun initListener() {

    }
}