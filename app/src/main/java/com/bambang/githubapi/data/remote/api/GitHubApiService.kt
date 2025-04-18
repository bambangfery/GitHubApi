package com.bambang.githubapi.data.remote.api

import com.bambang.githubapi.data.remote.model.GitHubUserDetailResponse
import com.bambang.githubapi.data.remote.model.GitHubUserRepoResponse
import com.bambang.githubapi.data.remote.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApiService {
    @GET("users")
    suspend fun getUserList(
        @Query("since") since: Int,
        @Query("per_page") perPage: Int
    ): Response<List<UserResponse>>

    @GET("users/{username}")
    suspend fun getUserDetail(
        @Path("username") username: String
    ): Response<GitHubUserDetailResponse>

    @GET("users/{username}/repos")
    suspend fun getUserRepos(
        @Path("username") username: String
    ): Response<List<GitHubUserRepoResponse>>

}