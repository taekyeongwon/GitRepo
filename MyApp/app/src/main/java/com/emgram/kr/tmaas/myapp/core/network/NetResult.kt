package com.emgram.kr.tmaas.myapp.core.network

import com.emgram.kr.tmaas.myapp.core.network.base.BaseResponse
import com.emgram.kr.tmaas.myapp.core.network.error.AppError

enum class Status {
    SUCCESS, FAIL//, LOADING
}
data class NetResult<T: BaseResponse>(val status: Status, val data: T?, val throwable: AppError.Base?) {    //응답 객체를 핸들링 하기 위한 데이터 클래스(성공, 실패 두가지)
    companion object {
        fun <T: BaseResponse> success(data: T?): NetResult<T> = NetResult(status = Status.SUCCESS, data = data, throwable = null)
//        fun <T: BaseResponse> loading(): NetResult<T> = NetResult(status = Status.LOADING, data = null, throwable = null)
        fun <T: BaseResponse> error(error: AppError.Base): NetResult<T> = NetResult(status = Status.FAIL, data = null, throwable = error)
    }
}

interface ResultCallback <T: BaseResponse>{ //콜백으로 Result 데이터클래스 받기 위한 인터페이스
    fun onResponse(response: NetResult<T>)
}