package com.bambang.githubapi.domain.usecase

import com.bambang.githubapi.data.mapper.toDomain
import com.bambang.githubapi.domain.model.GitHubUser
import com.bambang.githubapi.domain.repository.UserRepository
import javax.inject.Inject

class GetGitHubUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend fun execute(since: Int, page: Int, pageSize: Int): Result<List<GitHubUser>> {
        val offset = page * pageSize
        val currentCount = repository.getLocalListUserCount()

        if (offset >= currentCount) {
            val result = repository.fetchListUserGithub(since, pageSize)
            if (result.isFailure) {
                return Result.failure(result.exceptionOrNull() ?: Exception("Failed to fetch data"))
            }
        }

        val list = repository.getLocalListUserPage(pageSize, offset).map { it.toDomain() }
        return Result.success(list)
    }
}