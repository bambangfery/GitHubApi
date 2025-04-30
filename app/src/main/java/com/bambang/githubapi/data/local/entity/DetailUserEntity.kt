package com.bambang.githubapi.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_detail")
class DetailUserEntity (
    @PrimaryKey val id: Int,
    val login: String,
    val avatarUrl: String,
    val htmlUrl: String,
    val name: String?,
    val publicRepos: Int,
    val followers: Int,
    val following: Int
)