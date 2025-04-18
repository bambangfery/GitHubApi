package com.bambang.githubapi.presentation.home

import app.cash.turbine.test
import com.bambang.githubapi.domain.model.GitHubUser
import com.bambang.githubapi.domain.usecase.GetGitHubUserUseCase
import com.bambang.githubapi.presentation.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class GitHubUserViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: GitHubUserViewModel

    @Mock
    private lateinit var getGitHubUserUseCase: GetGitHubUserUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = GitHubUserViewModel(getGitHubUserUseCase)
    }

    @Test
    fun `loadNextUsers success should update userList`() = runTest {
        val dummyUsers = listOf(
            GitHubUser(id = 1, login = "user1", avatarUrl = "https://example.com/avatar1.png", url = "https://github.com/user1"),
            GitHubUser(id = 2, login = "user2", avatarUrl = "https://example.com/avatar2.png", url = "https://github.com/user2")
        )

        whenever(getGitHubUserUseCase.execute(0, 0, 10)).thenReturn(Result.success(dummyUsers))

        viewModel.loadNextUsers()

        viewModel.userList.test {
            val result = awaitItem()
            assertEquals(dummyUsers, result)
        }
    }

    @Test
    fun `loadNextUsers failure should update error state`() = runTest {
        val exception = Exception("Network error")
        whenever(getGitHubUserUseCase.execute(0, 0, 10)).thenReturn(Result.failure(exception))

        viewModel.loadNextUsers()

        viewModel.error.test {
            val result = awaitItem()
            assertEquals("Network error", result)
        }
    }

    @Test
    fun `searchUsers with matching query should filter userList`() = runTest {
        val users = listOf(
            GitHubUser(id = 1, login = "andi", avatarUrl = "https://example.com/avatar1.png", url = "https://github.com/user1"),
            GitHubUser(id = 2, login = "budi", avatarUrl = "https://example.com/avatar2.png", url = "https://github.com/user2")
        )

        viewModel = GitHubUserViewModel(getGitHubUserUseCase)
        viewModel.run {
            _userList.value = users
            searchUsers("and")
        }

        viewModel.filteredUserList.test {
            val result = awaitItem()
            assertEquals(1, result.size)
            assertEquals("andi", result.first().login)
        }
    }
}