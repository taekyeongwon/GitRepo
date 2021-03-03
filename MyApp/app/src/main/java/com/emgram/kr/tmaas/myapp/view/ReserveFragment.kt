package com.emgram.kr.tmaas.myapp.view

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.emgram.kr.tmaas.myapp.R
import com.emgram.kr.tmaas.myapp.base.BaseFragment
import com.emgram.kr.tmaas.myapp.core.custom.adapter.TestAdapter
import com.emgram.kr.tmaas.myapp.core.manager.PagingManager
import com.emgram.kr.tmaas.myapp.core.util.TMProviderFactory
import com.emgram.kr.tmaas.myapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_reserve.*
import java.lang.Exception

class ReserveFragment: BaseFragment<MainViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_reserve
    override lateinit var viewModel: MainViewModel

    private lateinit var pagingManager: PagingManager
    private lateinit var adapter: TestAdapter

    override fun initView() {
        try {
            viewModel = ViewModelProvider(requireActivity(), TMProviderFactory()).get(MainViewModel::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        adapter = TestAdapter()
        test_rv.adapter = adapter

        pagingManager = PagingManager(test_rv)
        pagingManager.register(test_rv)

        viewModel.coTest2()
    }

    override fun initObserver() {
        viewModel.live.observe(this, Observer {
            pagingManager.setCurrentPage(it.page)
            val list = ArrayList<String>()
            for(i in 0 until 30) {
                list.add(i.toString())
            }
            adapter.setText(list)
        })
    }

    override fun initListener() {
        pagingManager.setCallback(object: PagingManager.PagerCallback {
            override fun isLoading(): Boolean {
                return viewModel.progressFlag.value ?: false
            }

            override fun loadMoreItems(currentPage: Int) {
                viewModel.coTest2()
            }
        })
    }

    override fun onDestroyView() {
        pagingManager.unregister(test_rv)
        super.onDestroyView()
    }
}