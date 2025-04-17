package com.bambang.githubapi.data.remote.api

import com.bambang.githubapi.data.remote.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApiService {
    @GET("users")
    suspend fun getUserList(
        @Query("since") since: Int,
        @Query("per_page") perPage: Int
    ): Response<List<UserResponse>>

}