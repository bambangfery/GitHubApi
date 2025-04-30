package com.bambang.githubapi.domain.model

data class GitHubUser(
    val id: Int,
    val login: String,
    val avatarUrl: String,
    val url: String
)