package com.andreh.chatmockapp.ui.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.andreh.chatmockapp.R
import com.andreh.chatmockapp.data.db.users.User
import com.andreh.chatmockapp.databinding.UserItemBinding
import com.bumptech.glide.Glide

class UsersAdapter(private val clickListener:(User)->Unit)
    : RecyclerView.Adapter<UserViewHolder>()
{
    private val usersList = ArrayList<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : UserItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.user_item,parent,false)
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return usersList.size
    }


    fun setList(subscribers: List<User>) {
        usersList.clear()
        usersList.addAll(subscribers)

    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = usersList.get(holder.adapterPosition)
        if (position%2 == 0){
            item.photo = R.drawable.p1
        }else{
            item.photo = R.drawable.p2
        }
        holder.bind(usersList[position],clickListener)
    }

}

class UserViewHolder(val binding: UserItemBinding):RecyclerView.ViewHolder(binding.root){

    fun bind(user: User, clickListener:(User)->Unit){
        binding.username.text = user.name
        Glide.with(binding.root).load(user.photo).circleCrop().into(binding.userphoto)
        binding.listItemLayout.setOnClickListener{
            clickListener(user)
        }
    }
}