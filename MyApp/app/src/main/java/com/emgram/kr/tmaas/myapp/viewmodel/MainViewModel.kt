package com.emgram.kr.tmaas.myapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.emgram.kr.tmaas.myapp.base.*
import com.emgram.kr.tmaas.myapp.core.manager.Page
import com.emgram.kr.tmaas.myapp.core.network.NetResult
import com.emgram.kr.tmaas.myapp.core.network.ResultCallback
import com.emgram.kr.tmaas.myapp.core.network.Status
import com.emgram.kr.tmaas.myapp.model.GithubRepos
import com.emgram.kr.tmaas.myapp.model.MainModel
import kotlinx.coroutines.*


class MainViewModel(private val model: MainModel): BaseViewModel() {
    val live = MutableLiveData<GithubRepos>()

    var i = 1
//    val live2 = MutableLiveData<NetResult<GithubRepos>>()

//    fun coTest0() {
//        viewModelScope.launch {
//            live2.postValue(NetResult.loading())
//            val response = model.onDoingSomething("a")
//            live2.postValue(response)
//        }
//    }

    fun coTest() {
        viewModelScope.launch {
            val start = System.currentTimeMillis()
            _progressFlag.postValue(true)
            val a = viewModelScope.launch {
                val response = model.onDoingSomething("a")//coroutineCall{ model.onDoingSomething("a") }
                when(response.status) {
                    Status.SUCCESS -> live.postValue(response.data)
                    Status.FAIL -> _alertFlag.postValue(response.throwable)
                }
            }
            val b = viewModelScope.launch {
                val response = model.onDoingSomething("b")
                when(response.status) {
                    Status.SUCCESS -> live.postValue(response.data)
                    Status.FAIL -> _alertFlag.postValue(response.throwable)
                }
            }
            joinAll(a, b)
            _progressFlag.postValue(false)
            println(System.currentTimeMillis() - start)
        }
    }

    fun coTest2() {
        viewModelScope.launch {
            _progressFlag.postValue(true)
            val response = model.onDoingSomething("c")
            response.data?.page = Page(i++, 50)
            _progressFlag.postValue(false)
            when(response.status) {
                Status.SUCCESS -> live.postValue(response.data)
                Status.FAIL -> _alertFlag.postValue(response.throwable)
            }
        }
    }

    fun coTest3() {
//        viewModelScope.launch {

            val start = System.currentTimeMillis()
            _progressFlag.postValue(true)
            model.onDoingSomethingRetrofit("d", object: ResultCallback<GithubRepos> {
                override fun onResponse(response: NetResult<GithubRepos>) {
                    _progressFlag.postValue(false)
                    when(response.status) {
                        Status.SUCCESS -> live.postValue(response.data)
                        Status.FAIL -> _alertFlag.postValue(response.throwable)
                    }
                }
            })
//                when(response.status) {
//                    Status.SUCCESS -> live.postValue(response.data)
//                    Status.FAIL -> _alertFlag.postValue(response.throwable)
//                }

            println(System.currentTimeMillis() - start)
//        }
    }

    fun coTest4() {
//        viewModelScope.launch {

            val start = System.currentTimeMillis()
            _progressFlag.postValue(true)
            model.notFound(object: ResultCallback<GithubRepos> {
                override fun onResponse(response: NetResult<GithubRepos>) {
                    _progressFlag.postValue(false)
                    when(response.status) {
                        Status.SUCCESS -> live.postValue(response.data)
                        Status.FAIL -> _alertFlag.postValue(response.throwable)
                    }
                }
            })
            println(System.currentTimeMillis() - start)
//        }
    }

}