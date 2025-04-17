package com.bambang.githubapi.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bambang.githubapi.data.local.entity.GitHubUserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<GitHubUserEntity>)

    @Query("SELECT * FROM users ORDER BY id ASC  LIMIT :limit OFFSET :offset")
    suspend fun getUsersPaginated(limit: Int, offset: Int): List<GitHubUserEntity>

    @Query("SELECT COUNT(*) FROM users")
    suspend fun getListUserCount(): Int

}