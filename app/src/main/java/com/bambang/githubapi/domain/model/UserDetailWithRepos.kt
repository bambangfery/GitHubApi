package com.bambang.githubapi.domain.model

data class UserDetailWithRepos(
    val userDetail: UserDetail?,
    val repoList: List<UserRepo>
)