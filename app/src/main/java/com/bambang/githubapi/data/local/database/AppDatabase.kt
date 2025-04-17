package com.bambang.githubapi.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bambang.githubapi.data.local.dao.UserDao
import com.bambang.githubapi.data.local.entity.GitHubUserEntity

@Database(entities = [GitHubUserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}