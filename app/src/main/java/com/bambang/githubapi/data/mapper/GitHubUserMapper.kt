package com.bambang.githubapi.data.mapper

import com.bambang.githubapi.data.local.entity.GitHubUserEntity
import com.bambang.githubapi.data.remote.model.UserResponse
import com.bambang.githubapi.domain.model.GitHubUser

fun GitHubUserEntity.toResponse(): UserResponse {
    return UserResponse(id, login, avatarUrl, htmlUrl)
}

fun UserResponse.toEntity(): GitHubUserEntity {
    return GitHubUserEntity(id, login, avatar_url, html_url)
}

fun GitHubUserEntity.toDomain(): GitHubUser {
    return GitHubUser(id, login, avatarUrl, htmlUrl)
}