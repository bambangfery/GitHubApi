package com.bambang.githubapi.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bambang.githubapi.databinding.ItemListUserBinding
import com.bambang.githubapi.domain.model.GitHubUser
import com.bumptech.glide.Glide

class ListUserAdapter(
    private val onClick: (GitHubUser) -> Unit
) : ListAdapter<GitHubUser, ListUserAdapter.ListUserViewHolder>(DiffCallback()) {

    inner class ListUserViewHolder(private val binding: ItemListUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: GitHubUser) {
            binding.tvUserGithub.text = user.login.replaceFirstChar { it.uppercaseChar()}
            Glide.with(binding.imgUserGithub.context).load(user.avatarUrl).into(binding.imgUserGithub)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListUserViewHolder {
        val binding = ItemListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListUserViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onClick(item)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<GitHubUser>() {
        override fun areItemsTheSame(oldItem: GitHubUser, newItem: GitHubUser): Boolean {
            return oldItem.login == newItem.login
        }

        override fun areContentsTheSame(oldItem: GitHubUser, newItem: GitHubUser): Boolean {
            return oldItem.login == newItem.login
        }
    }
}