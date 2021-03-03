package com.emgram.kr.tmaas.myapp.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.emgram.kr.tmaas.myapp.core.alert.MyAlertDialog
import com.emgram.kr.tmaas.myapp.core.alert.MyProgressDialog
import java.lang.Exception

abstract class BaseFragment<T: BaseViewModel>: Fragment() {
    abstract val layoutResourceId: Int
    abstract var viewModel: T

    abstract fun initView()
    abstract fun initObserver()
    abstract fun initListener()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutResourceId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
        initListener()
        observeAlert()
        observeProgress()
    }

    private fun observeAlert() {
        try {
            viewModel.alertFlag.observe(requireActivity(), Observer {
                context?.let { con ->
                    MyAlertDialog.showAlert(con, it.getMessage(con))
                    Log.d("Alert", it.getMessage(con))
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun observeProgress() {
        try {
            viewModel.progressFlag.observe(requireActivity(), Observer {
                context?.let { con ->
                    if (it) {
                        MyProgressDialog.showAlert(con)
                    } else {
                        MyProgressDialog.hide()
                    }
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
            MyProgressDialog.hide()
        }
    }
}