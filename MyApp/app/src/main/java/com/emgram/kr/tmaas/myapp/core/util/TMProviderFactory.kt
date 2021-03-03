package com.emgram.kr.tmaas.myapp.core.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.emgram.kr.tmaas.myapp.core.network.MainApiServer
import com.emgram.kr.tmaas.myapp.model.MainModelImpl
import com.emgram.kr.tmaas.myapp.viewmodel.MainViewModel
import java.lang.IllegalArgumentException

class TMProviderFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when(modelClass) {
            MainViewModel::class.java -> MainViewModel(MainModelImpl(MainApiServer())) as T
            else -> throw IllegalArgumentException("Unknown Class")
        }
    }
}