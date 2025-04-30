package com.bambang.githubapi.data.mapper

import com.bambang.githubapi.data.local.entity.DetailUserEntity
import com.bambang.githubapi.data.local.entity.GitHubUserEntity
import com.bambang.githubapi.data.local.entity.RepoUserEntity
import com.bambang.githubapi.data.remote.model.GitHubUserDetailResponse
import com.bambang.githubapi.data.remote.model.GitHubUserRepoResponse
import com.bambang.githubapi.data.remote.model.UserResponse
import com.bambang.githubapi.domain.model.GitHubUser
import com.bambang.githubapi.domain.model.UserDetail
import com.bambang.githubapi.domain.model.UserRepo

fun UserResponse.toEntity(): GitHubUserEntity {
    return GitHubUserEntity(id, login, avatar_url, url)
}

fun GitHubUserEntity.toDomain(): GitHubUser {
    return GitHubUser(id, login, avatarUrl, url)
}

fun GitHubUserDetailResponse.toEntity(): DetailUserEntity {
    return DetailUserEntity(id, login, avatar_url, html_url, name, public_repos, followers, following)
}
fun DetailUserEntity.toDomain(): UserDetail {
    return UserDetail(login, id, avatarUrl, htmlUrl, name, publicRepos, followers, following)
}

fun GitHubUserRepoResponse.toEntity() : RepoUserEntity {
    return RepoUserEntity(id, userId = owner.id, name, html_url, description)
}

fun RepoUserEntity.toDomain(): UserRepo {
    return UserRepo(id, name, htmlUrl, description)
}