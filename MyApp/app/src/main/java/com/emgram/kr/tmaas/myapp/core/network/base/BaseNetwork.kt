package com.emgram.kr.tmaas.myapp.core.network.base

import com.emgram.kr.tmaas.myapp.core.network.NetResult
import com.emgram.kr.tmaas.myapp.core.network.ResultCallback
import retrofit2.Call

/**
 * Network 통신용 추상화 클래스.
 * createNetwork 재정의하여 각 라이브러리에 맞는 초기화 작업
 * parsingResponse 재정의하여 각 라이브러리에 맞는 응답값 파싱 작업
 *
 * T = library object (ex : Retrofit)
 * U = response object (ex : Response<out BaseResponse>)
 */

abstract class BaseNetwork <T>: BusinessInterface {
    protected var network: T

    init {
        network = createNetwork()
    }

    abstract fun createNetwork(): T
}

interface BusinessInterface {
    val API: BaseApiProtocol    //api 호출용 변수
    fun <V : BaseResponse> parsingResponse(call: Call<V>, callback: ResultCallback<V>)              //콜백으로 응답 처리하는 경우 호출
    suspend fun <V: BaseResponse> parsingResponseSuspend(call: suspend ()-> Any): NetResult<V>    //코루틴으로 응답 처리하는 경우 호출
}

interface ServerResult {
    fun isSuccess(): Boolean
    fun resultCode(): String
    fun errorMessage(): String?
}