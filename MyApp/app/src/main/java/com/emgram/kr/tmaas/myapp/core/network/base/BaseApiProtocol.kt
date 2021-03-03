package com.emgram.kr.tmaas.myapp.core.network.base

import com.emgram.kr.tmaas.myapp.model.GithubRepos
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Query

/**
 * 비즈니스 레이어 상위 인터페이스.
 */
interface BaseApiProtocol {
    suspend fun getRepositories(@Query("q") query: String): Response<GithubRepos>
    fun getRepositories2(@Query("q") query: String): Call<GithubRepos>
    fun notFound(@Query("q") query: String): Call<GithubRepos>
}