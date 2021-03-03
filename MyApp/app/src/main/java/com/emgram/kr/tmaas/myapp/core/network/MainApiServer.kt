package com.emgram.kr.tmaas.myapp.core.network

import com.emgram.kr.tmaas.myapp.BuildConfig
import com.emgram.kr.tmaas.myapp.MainApplication
import com.emgram.kr.tmaas.myapp.R
import com.emgram.kr.tmaas.myapp.core.network.base.BaseNetwork
import com.emgram.kr.tmaas.myapp.core.network.base.BaseResponse
import com.emgram.kr.tmaas.myapp.core.network.base.ServerResult
import com.emgram.kr.tmaas.myapp.core.network.error.AppError
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.ClassCastException
import java.lang.Exception
import java.util.concurrent.TimeUnit

/**
 * BaseNetwork 추상화 클래스를 재정의한 Retrofit에 의존적인 클래스
 */
class MainApiServer: BaseNetwork<Retrofit>() {  //Retrofit 라이브러리 객체 사용
    private val TAG = "MainApiServer"
    override val API: MainApiProtocol = network.create(MainApiProtocol::class.java)   //Retrofit 비즈니스 레이어(MainApiProtocol) 사용하여 통신하기 위한 객체

    override fun createNetwork(): Retrofit {
        val interceptor = HttpLoggingInterceptor()  //okhttp3 로그 출력용 인터셉터
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().apply {
            writeTimeout(10, TimeUnit.SECONDS) // default
            if (BuildConfig.DEBUG) {
                addInterceptor(interceptor)
            }
            addInterceptor(MainApiInterceptor())
        }.build()

        return Retrofit.Builder()
            .baseUrl(MainApplication.application?.getString(R.string.base_url))
            .client(client)
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //람다식 실행 결과를 Any로 업캐스팅해서 받음. 라이브러리마다 필요한 응답 객체로 다운캐스팅해서 사용하기 위함.
    //NetResult 클래스는 BaseResponse를 상한 클래스로 정의.
    @Suppress("UNCHECKED_CAST")
    override suspend fun <V : BaseResponse> parsingResponseSuspend(call: suspend () -> Any): NetResult<V> { //코루틴 사용하는 경우 호출
        val response: Response<V>
        try {
            response = call.invoke() as Response<V>    //네트워크 통신 실패 시 exception 발생. 업캐스팅된 람다식의 결과값을 Response<V>로 다운캐스팅
            return if(response.isSuccessful) {     //200ok
                val base = response.body()
                if(base is ServerResult && !base.isSuccess()) {  //200ok이면서 서버 커스텀 에러 처리
                    NetResult.error(AppError.Server(base))
                } else {
                    NetResult.success(response.body())
                }
            } else {
                NetResult.error(AppError.Network(response.code()))  //response code 200 외(300~500) 에러
            }
        } catch (ce: ClassCastException) {  //람다식 리턴값 Any로 업캐스팅 후 BaseResponse의 자식클래스(V)로 다운캐스팅 할 때 캐스팅 익셉션 발생 시 강제종료
            ce.printStackTrace()
            throw AssertionError("Called api's return type is must be BaseResponse's subclass!!")
        } catch (e: Exception) {
            e.printStackTrace()
            return NetResult.error(AppError.Network(-1))   //네트워크 통신 실패 시 에러처리
        }

    }

    override fun <V : BaseResponse> parsingResponse(call: Call<V>, callback: ResultCallback<V>) {    //코루틴 사용 없이 콜백 사용하는 경우 호출
        call.enqueue(object: Callback<V> {
            override fun onFailure(call: Call<V>, t: Throwable) {
                callback.onResponse(NetResult.error(AppError.Network(-1, t)))  //네트워크 통신 실패 시 에러처리
            }

            override fun onResponse(call: Call<V>, response: Response<V>) {
                if(response.isSuccessful) {
                    val base = response.body()
                    if(base is ServerResult && !base.isSuccess()) {  //200ok이면서 서버 커스텀 에러 처리
                        callback.onResponse(NetResult.error(AppError.Server(base)))
                    } else {
                        callback.onResponse(NetResult.success(response.body()))
                    }
                } else {
                    callback.onResponse(NetResult.error(AppError.Network(response.code())))  //200 외(300~500) 에러
                }
            }
        })
    }
}