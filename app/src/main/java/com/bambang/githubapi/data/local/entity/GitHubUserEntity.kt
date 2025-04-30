package com.bambang.githubapi.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class GitHubUserEntity(
    @PrimaryKey val id: Int,
    val login: String,
    val avatarUrl: String,
    val url: String
)