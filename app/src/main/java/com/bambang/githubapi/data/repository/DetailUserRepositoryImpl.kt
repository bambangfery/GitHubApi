package com.bambang.githubapi.data.repository

import com.bambang.githubapi.data.local.dao.DetailUserDao
import com.bambang.githubapi.data.local.dao.RepoUserDao
import com.bambang.githubapi.data.mapper.toDomain
import com.bambang.githubapi.data.mapper.toEntity
import com.bambang.githubapi.data.remote.api.GitHubApiService
import com.bambang.githubapi.domain.model.UserDetail
import com.bambang.githubapi.domain.model.UserRepo
import com.bambang.githubapi.domain.repository.DetailUserRepository
import javax.inject.Inject

class DetailUserRepositoryImpl @Inject constructor(
    private val api: GitHubApiService,
    private val detailUserDao: DetailUserDao,
    private val repoUserDao: RepoUserDao
) : DetailUserRepository {

    override suspend fun storeUserData(username: String): Result<Int> {
        return try {
            val respDetail = api.getUserDetail(username)
            if (respDetail.isSuccessful) {
                respDetail.body()?.let {
                    val userId = it.id
                    detailUserDao.insertDetail(it.toEntity())
                    if (it.public_repos > 0){
                        val respRepo = api.getUserRepos(username)
                        if (respRepo.isSuccessful && respRepo.body() != null) {
                            val repo = respRepo.body()?.map { it.toEntity() } ?: emptyList()
                            repoUserDao.insertAll(repo)
                            Result.success(userId)
                        } else {
                            Result.failure(Exception("API error ${respRepo.code()}"))
                        }
                    } else {
                        Result.success(userId)
                    }
                } ?: Result.failure(Exception("Empty Body"))
            } else {
                Result.failure(Exception("API error ${respDetail.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(Exception(e.message ?: "Unknown error"))
        }
    }

    override suspend fun getDetailUser(userId: Int): UserDetail?{
        val result = detailUserDao.getDetail(userId)
        return result?.toDomain()
    }

    override suspend fun getRepoUser(userId: Int): List<UserRepo> {
        val result = repoUserDao.getRepos(userId)
        return result.map { it.toDomain() }
    }

}