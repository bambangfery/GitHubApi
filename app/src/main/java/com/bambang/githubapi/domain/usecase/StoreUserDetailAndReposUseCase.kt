package com.bambang.githubapi.domain.usecase

import com.bambang.githubapi.domain.repository.DetailUserRepository
import javax.inject.Inject

class StoreUserDetailAndReposUseCase @Inject constructor(
    private val repo: DetailUserRepository
) {
    suspend fun execute(username: String): Result<Int> {
        return repo.storeUserData(username)
    }
}