package com.testkrealogi.testgithubuser.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.testkrealogi.testgithubuser.R
import com.testkrealogi.testgithubuser.databinding.UserItemBinding
import com.testkrealogi.testgithubuser.model.User

class UserAdapter(private val users: List<User>, private val picasso: Picasso): RecyclerView.Adapter<UserAdapter.UserHolder>() {

  private lateinit var binding: UserItemBinding
  var onTapUserListener: ((User) -> Unit)? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
    binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    return UserHolder(binding)
  }

  override fun getItemCount(): Int {
    return users.size
  }

  override fun onBindViewHolder(holder: UserHolder, position: Int) {
    holder.bindData(users[position])
  }

  inner class UserHolder(private val binding: UserItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bindData(user: User) {
      binding.userName.text = user.login

      picasso.load(user.avatarUrl)
        .error(android.R.drawable.ic_menu_close_clear_cancel)
        .placeholder(R.drawable.loading)
        .into(binding.userImage)

      binding.cardItem.setOnClickListener {
        onTapUserListener?.let { param -> param(user) }
      }
    }
  }
}
