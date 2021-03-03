package com.emgram.kr.tmaas.myapp.core.network

import com.emgram.kr.tmaas.myapp.core.network.base.BaseApiProtocol
import com.emgram.kr.tmaas.myapp.model.GithubRepos
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * POST @Body
 * PUT @Body
 * GET @Query
 * DELETE @Query
 *
 * @Multipart @Part part: MultipartBody.Part
 */
interface MainApiProtocol: BaseApiProtocol {

    /**
     * 토큰값 유효 인증
     */
//    @POST("api/v1/auth/validate_token")
//    fun checkTokenValidate() : Call<ValidResponse>
//    fun checkTokenValidate(@Body tokenRequest: TokenRequest) : Call<ValidResponse>

    /**
     * 테스트
     */
    @GET("search/repositories")
    override suspend fun getRepositories(@Query("q") query: String): Response<GithubRepos>

    @GET("search/repositories")
    override fun getRepositories2(@Query("q") query: String): Call<GithubRepos>

    @GET("search/repositoriess")
    override fun notFound(@Query("q") query: String): Call<GithubRepos>
}