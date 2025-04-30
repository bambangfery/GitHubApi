package com.bambang.githubapi.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repo")
class RepoUserEntity (
    @PrimaryKey val id: Int,
    val userId: Int,
    val name: String,
    val htmlUrl: String,
    val description: String?
)