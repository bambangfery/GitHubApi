package com.bambang.githubapi.data.remote.model

data class GitHubUserRepoResponse(
    val id: Int,
    val name: String,
    val html_url: String,
    val description: String?,
    val owner: Owner
)

data class Owner(
    val id: Int,
    val login: String
)