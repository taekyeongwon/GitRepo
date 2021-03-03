package com.emgram.kr.tmaas.myapp.model

import com.emgram.kr.tmaas.myapp.core.manager.Page
import com.emgram.kr.tmaas.myapp.core.network.*
import com.emgram.kr.tmaas.myapp.core.network.base.BaseResponse
import com.emgram.kr.tmaas.myapp.core.network.base.BusinessInterface

interface MainModel {
    suspend fun onDoingSomething(q: String): NetResult<GithubRepos>
    fun onDoingSomethingRetrofit(q: String, callback: ResultCallback<GithubRepos>)
    fun notFound(callback: ResultCallback<GithubRepos>)
}

data class GithubRepos(val totalCount: Int, val items: List<GithubRepo>, var page: Page? = null): BaseResponse()
data class GithubRepo(val id: Long, val fullName: String, val htmlUrl: String, val description: String, val stargazersCount: Int, val owner: GithubRepoOwner)
data class GithubRepoOwner(val avatarUrl: String)

class MainModelImpl (val apiServer: BusinessInterface): MainModel {
    override suspend fun onDoingSomething(q: String): NetResult<GithubRepos> {
        return apiServer.parsingResponseSuspend(call = {apiServer.API.getRepositories(q)})
        //apiServer.parsingResponse<GithubRepos>(response) 리턴 타입으로 추론하여 <GithubRepos> 생략가능

        //참고
        //val parsing: NetResult<GithubRepos> = apiServer.parsingResponseSuspend(response)
        //return parsing
        //변수로 선언 하는 경우 변수 타입을 명시적으로 지정해줘야 추론 가능
    }

    override fun onDoingSomethingRetrofit(q: String, callback: ResultCallback<GithubRepos>) {
        apiServer.parsingResponse(apiServer.API.getRepositories2(q), callback)  //코루틴 사용하지 않고 콜백을 명시적으로 사용
    }

    override fun notFound(callback: ResultCallback<GithubRepos>) {
        apiServer.parsingResponse(apiServer.API.notFound(""), callback)
    }
}