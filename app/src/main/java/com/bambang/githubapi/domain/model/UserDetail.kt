package com.bambang.githubapi.domain.model

class UserDetail (
    val login: String,
    val id: Int,
    val avatarUrl: String,
    val htmlUrl: String,
    val name: String?,
    val publicRepos: Int,
    val followers: Int,
    val following: Int
)