package com.bambang.githubapi.domain.repository

import com.bambang.githubapi.data.local.entity.GitHubUserEntity

interface UserRepository {
    suspend fun fetchListUserGithub(since: Int, page: Int): Result<Unit>
    suspend fun getLocalListUserPage(limit: Int, offset: Int): List<GitHubUserEntity>
    suspend fun getLocalListUserCount(): Int
}