package com.bambang.githubapi.domain.repository

import com.bambang.githubapi.domain.model.UserDetail
import com.bambang.githubapi.domain.model.UserRepo


interface DetailUserRepository {
    suspend fun storeUserData(username: String): Result<Int>
    suspend fun getDetailUser(userId: Int): UserDetail?
    suspend fun getRepoUser(userId: Int): List<UserRepo>
}