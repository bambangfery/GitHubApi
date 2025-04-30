package com.bambang.githubapi.domain.usecase

import com.bambang.githubapi.domain.model.UserDetailWithRepos
import com.bambang.githubapi.domain.repository.DetailUserRepository
import javax.inject.Inject

class GetUserDetailAndReposUseCase @Inject constructor(
    private val repo: DetailUserRepository
) {
    suspend fun execute(userId: Int): Result<UserDetailWithRepos> {
        val detail = repo.getDetailUser(userId)
        val repository = repo.getRepoUser(userId)
        return Result.success(UserDetailWithRepos(detail, repository))
    }
}