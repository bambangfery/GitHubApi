package com.bambang.githubapi.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bambang.githubapi.databinding.ActivityGitHubUserBinding
import com.bambang.githubapi.presentation.detail.DetailUserActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GitHubUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGitHubUserBinding
    private val viewModel: GitHubUserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGitHubUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ListUserAdapter { selectedUser ->
            val intent = Intent(this, DetailUserActivity::class.java)
            intent.putExtra("name", selectedUser.login)
            startActivity(intent)
        }

        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.adapter = adapter

        binding.rvUsers.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = rv.layoutManager as LinearLayoutManager
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount
                if (lastVisibleItem + 2 >= totalItemCount && binding.searchView.query.isEmpty()) {
                    viewModel.loadNextUsers()
                }
            }
        })

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchUsers(query ?: "")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchUsers(newText ?: "")
                return true
            }
        })

        lifecycleScope.launch {
            viewModel.userList.collectLatest { list ->
                adapter.submitList(list.toList())
            }
        }

        lifecycleScope.launch {
            viewModel.filteredUserList.collectLatest { list ->
                adapter.submitList(list.map { it.copy() })
                binding.tvEmptyState.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
            }
        }

        viewModel.loadNextUsers()
    }
}