package com.example.simpleroomretroapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.simpleroomretroapp.databinding.UserItemBinding
import com.example.simpleroomretroapp.models.UserData
import com.example.simpleroomretroapp.utilities.CustomDiffUtil
import com.example.simpleroomretroapp.utilities.OnUserClickListener

class UserRecyclerAdapter(var onUserClickListener: OnUserClickListener): RecyclerView.Adapter<UserRecyclerAdapter.UserHolder>() {
    var userList: List<UserData> = arrayListOf()

    inner class UserHolder(private val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            binding.root.setOnClickListener(this)
        }
        fun bind(userData: UserData) {
            binding.tvFirstName.text = userData.firstName
            binding.tvLastName.text = userData.lastName
            binding.tvEmail.text = userData.email
            binding.tvId.text = userData.id.toString()
            Glide.with(binding.root.context)
                .load(userData.avatar)
                .into(binding.iv)
        }

        override fun onClick(v: View?) {
            onUserClickListener.onUserClick(userList[adapterPosition])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserHolder(binding)
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    /**
     * updating user list for recycler view
     */
    fun setData(newUserList: List<UserData>) {
        val userDiffUtil = CustomDiffUtil(userList, newUserList)
        val diffUtilResult = DiffUtil.calculateDiff(userDiffUtil)
        userList = newUserList
        diffUtilResult.dispatchUpdatesTo(this)
    }

    fun getItemByPosition(position: Int): UserData {
        return userList[position]
    }
}