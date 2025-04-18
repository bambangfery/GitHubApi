package com.bambang.githubapi.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bambang.githubapi.domain.model.GitHubUser
import com.bambang.githubapi.domain.usecase.GetGitHubUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GitHubUserViewModel @Inject constructor(
    private val getGitHubUserUseCase: GetGitHubUserUseCase
) : ViewModel() {

    val _userList = MutableStateFlow<List<GitHubUser>>(emptyList())
    val userList: StateFlow<List<GitHubUser>> = _userList

    private val _filteredUsers = MutableStateFlow<List<GitHubUser>>(emptyList())
    val filteredUserList: StateFlow<List<GitHubUser>> = _filteredUsers

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private var isLoading = false
    private var lastSince: Int = 0
    private var currentPage = 0
    private val perPage = 10

    fun loadNextUsers() {
        if (isLoading) return
        isLoading = true

        viewModelScope.launch {
            val result = getGitHubUserUseCase.execute(lastSince, currentPage, perPage)

            result.onSuccess { newUsers ->
                _userList.update { old ->
                    val updated = old + newUsers
                    updated
                }

                if (newUsers.isNotEmpty()) {
                    lastSince = newUsers.last().id
                    currentPage++
                }

            }.onFailure {
                _error.value = it.message
            }

            isLoading = false
        }
    }

    fun searchUsers(query: String) {
        val lowerQuery = query.lowercase()
        _filteredUsers.value = if (query.isBlank()) {
            _userList.value
        } else {
            _userList.value.filter { it.login.contains(lowerQuery, ignoreCase = true) }
        }
    }
}