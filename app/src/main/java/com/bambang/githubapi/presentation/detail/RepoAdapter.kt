package com.bambang.githubapi.presentation.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bambang.githubapi.databinding.ItemRepoBinding
import com.bambang.githubapi.domain.model.UserRepo

class RepoAdapter(private val context: Context) :
    ListAdapter<UserRepo, RepoAdapter.RepoViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val binding = ItemRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class RepoViewHolder(private val binding: ItemRepoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(repo: UserRepo) {
            binding.tvRepoName.text = repo.name
            binding.root.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(repo.htmlUrl))
                context.startActivity(intent)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<UserRepo>() {
        override fun areItemsTheSame(oldItem: UserRepo, newItem: UserRepo) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: UserRepo, newItem: UserRepo) = oldItem.id == newItem.id
    }
}