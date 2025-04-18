package com.bambang.githubapi.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bambang.githubapi.data.local.entity.DetailUserEntity

@Dao
interface DetailUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetail(user: DetailUserEntity)

    @Query("SELECT * FROM user_detail WHERE id = :userId")
    suspend fun getDetail(userId: Int): DetailUserEntity?

    @Query("SELECT * FROM user_detail WHERE login = :username")
    suspend fun getDetailbyLogin(username: String): DetailUserEntity?
}