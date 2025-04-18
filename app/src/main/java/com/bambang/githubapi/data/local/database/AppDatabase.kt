package com.bambang.githubapi.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bambang.githubapi.data.local.dao.DetailUserDao
import com.bambang.githubapi.data.local.dao.RepoUserDao
import com.bambang.githubapi.data.local.dao.UserDao
import com.bambang.githubapi.data.local.entity.DetailUserEntity
import com.bambang.githubapi.data.local.entity.GitHubUserEntity
import com.bambang.githubapi.data.local.entity.RepoUserEntity

@Database(entities = [GitHubUserEntity::class, DetailUserEntity::class, RepoUserEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun detailUserDao(): DetailUserDao
    abstract fun repoUserDao(): RepoUserDao
}