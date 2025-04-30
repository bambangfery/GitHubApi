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
                        fetchRepoUser(userId,username)
                    } else {
                        Result.success(userId)
                    }
                } ?: Result.failure(Exception("Empty Body"))
            } else {
                fallbackToLocalUser(username)
            }
        } catch (e: Exception) {
            fallbackToLocalUser(username)
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

    private suspend fun fetchRepoUser(userId: Int, username: String): Result<Int>{
        try {
            val respRepo = api.getUserRepos(username)
            if (respRepo.isSuccessful && respRepo.body() != null) {
                val repo = respRepo.body()?.map { it.toEntity() } ?: emptyList()
                repoUserDao.insertAll(repo)
                return Result.success(userId)
            } else {
                return fallbackToLocalRepo(userId)
            }
        } catch (e: Exception){
            return fallbackToLocalRepo(userId)
        }
    }

    private suspend fun fallbackToLocalUser(username: String): Result<Int> {
        val detail = detailUserDao.getDetailbyLogin(username)
        return if (detail != null) {
            if (detail.publicRepos > 0){
                fetchRepoUser(detail.id,username)
            } else {
                Result.success(detail.id)
            }
        } else {
            Result.failure(Exception("No local data available"))
        }
    }

    private suspend fun fallbackToLocalRepo(userId: Int): Result<Int> {
        val result = repoUserDao.getRepos(userId)
        return if (result != null) {
            Result.success(userId)
        } else {
            Result.failure(Exception("No local data available"))
        }
    }
}