package com.bambang.githubapi.data.remote.model

data class UserResponse(
    val id: Int,
    val login: String,
    val avatar_url: String,
    val url: String
)