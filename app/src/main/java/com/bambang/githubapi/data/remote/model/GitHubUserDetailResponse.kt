package com.bambang.githubapi.data.remote.model

data class GitHubUserDetailResponse(
    val login: String,
    val id: Int,
    val avatar_url: String,
    val html_url: String,
    val name: String?,
    val public_repos: Int,
    val followers: Int,
    val following: Int
)