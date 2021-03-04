package com.emgram.kr.tmaas.myapp.view

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.ViewModelProvider
import com.emgram.kr.tmaas.myapp.R
import com.emgram.kr.tmaas.myapp.base.BaseFragment
import com.emgram.kr.tmaas.myapp.core.custom.adapter.TestSpinnerAdapter
import com.emgram.kr.tmaas.myapp.core.util.TMProviderFactory
import com.emgram.kr.tmaas.myapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.test_spinner.*
import java.lang.Exception

class HomeFragment: BaseFragment<MainViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_home
    override lateinit var viewModel: MainViewModel
    val list = ArrayList<String>()

    override fun initView() {
        try {
            viewModel = ViewModelProvider(requireActivity(), TMProviderFactory()).get(MainViewModel::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        list.clear()
        list.add("test1")
        list.add("test2")
        list.add("test3")
        list.add("test4")
        list.add("title")

    }

    override fun onResume() {
        super.onResume()
        val adapter = TestSpinnerAdapter(requireContext(), R.layout.test_spinner, list, "title")
        adapter.setDropDownViewResource(R.layout.item_test)
        test_sp.adapter = adapter
        test_sp.setSelection(adapter.count)
    }

    override fun initObserver() {

    }

    override fun initListener() {

    }
}