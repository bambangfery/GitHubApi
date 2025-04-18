package com.bambang.githubapi.presentation.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bambang.githubapi.databinding.ActivityDetailUserBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailUserActivity: AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private val viewModel: DetailUserViewModel by viewModels()
    private lateinit var repoAdapter: RepoAdapter

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Detail User Github"

        val username = intent.getStringExtra("name") ?: return
        repoAdapter = RepoAdapter(this)

        binding.recyclerRepos.layoutManager = LinearLayoutManager(this)
        binding.recyclerRepos.adapter = repoAdapter

        viewModel.loadUserDetail(username)

        lifecycleScope.launch {
            viewModel.userDetail.collectLatest { detail ->
                Glide.with(this@DetailUserActivity)
                    .load(detail?.avatarUrl)
                    .into(binding.imgAvatar)

                binding.tvUsername.text = detail?.login
                binding.tvPublicRepos.text = "Public Repos: ${detail?.publicRepos?:0}"
            }
        }

        lifecycleScope.launch {
            viewModel.userRepos.collectLatest {
                repoAdapter.submitList(it)
            }
        }

        lifecycleScope.launch {
            viewModel.isLoading.collectLatest { loading ->
                binding.progressBar.isVisible = loading
            }
        }

        lifecycleScope.launch {
            viewModel.error.collectLatest { msg ->
                msg?.let {
                    Toast.makeText(this@DetailUserActivity, msg, Toast.LENGTH_LONG).show()
                }
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}