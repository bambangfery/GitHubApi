package com.bambang.githubapi.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bambang.githubapi.data.local.entity.RepoUserEntity

@Dao
interface RepoUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<RepoUserEntity>)

    @Query("SELECT * FROM repo WHERE userId = :userId")
    suspend fun getRepos(userId: Int): List<RepoUserEntity>

    @Query("SELECT COUNT(*) FROM repo WHERE userId = :userId")
    suspend fun getListRepoCount(userId: Int): Int
}