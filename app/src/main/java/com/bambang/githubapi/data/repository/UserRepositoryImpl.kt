package com.bambang.githubapi.data.repository

import com.bambang.githubapi.data.local.dao.UserDao
import com.bambang.githubapi.data.local.entity.GitHubUserEntity
import com.bambang.githubapi.data.mapper.toEntity
import com.bambang.githubapi.data.remote.api.GitHubApiService
import com.bambang.githubapi.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: GitHubApiService,
    private val userDao: UserDao
) : UserRepository {

    override suspend fun fetchListUserGithub(since: Int, page: Int): Result<Unit> {
        return try {
            val response = api.getUserList(since, page)
            val body = response.body()

            if (response.isSuccessful && body != null) {
                val users = response.body()?.map { it.toEntity() }
                users?.let {
                    userDao.insertAll(it)
                    Result.success(Unit)
                } ?: Result.failure(Exception("No data"))
            } else {
                Result.failure(Exception("error ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getLocalListUserPage(limit: Int, offset: Int): List<GitHubUserEntity> {
        return userDao.getUsersPaginated(limit, offset)
    }

    override suspend fun getLocalListUserCount(): Int {
        return userDao.getListUserCount()
    }

}