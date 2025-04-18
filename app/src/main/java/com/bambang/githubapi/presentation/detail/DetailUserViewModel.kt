package com.bambang.githubapi.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bambang.githubapi.domain.model.UserDetail
import com.bambang.githubapi.domain.model.UserRepo
import com.bambang.githubapi.domain.usecase.GetUserDetailAndReposUseCase
import com.bambang.githubapi.domain.usecase.StoreUserDetailAndReposUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailUserViewModel @Inject constructor(
    private val storeUserDataUseCase: StoreUserDetailAndReposUseCase,
    private val getUserReposUseCase: GetUserDetailAndReposUseCase
) : ViewModel() {

    private val _userDetail = MutableStateFlow<UserDetail?>(null)
    val userDetail: StateFlow<UserDetail?> = _userDetail

    private val _userRepos = MutableStateFlow<List<UserRepo>>(emptyList())
    val userRepos: StateFlow<List<UserRepo>> = _userRepos

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadUserDetail(username: String) {
        _isLoading.value = true
        viewModelScope.launch {
            storeUserDataUseCase.execute(username)
                .onSuccess { userid ->
                    getUserReposUseCase.execute(userid)
                        .onSuccess {
                            _userDetail.value = it.userDetail
                            _userRepos.value = it.repoList
                        } }
                .onFailure { _error.value = it.message ?: "Terjadi kesalahan" }
            _isLoading.value = false
        }
    }
}