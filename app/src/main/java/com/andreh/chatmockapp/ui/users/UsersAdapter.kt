package com.andreh.chatmockapp.ui.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.andreh.chatmockapp.R
import com.andreh.chatmockapp.data.db.users.UserWithMessages
import com.andreh.chatmockapp.databinding.UserItemBinding
import com.bumptech.glide.Glide

class UsersAdapter(private val clickListener:(UserWithMessages)->Unit)
    : RecyclerView.Adapter<UserViewHolder>()
{
    private val userWithMessages = ArrayList<UserWithMessages>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : UserItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.user_item,parent,false)
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userWithMessages.size
    }


    fun setList(userMessages: List<UserWithMessages>) {
        userWithMessages.clear()
        userWithMessages.addAll(userMessages)

    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = userWithMessages.get(holder.adapterPosition)
        if (position%2 == 0){
            item.owner.photo = R.drawable.p1
        }else{
            item.owner.photo = R.drawable.p2
        }
        holder.bind(userWithMessages[position],clickListener)
    }

}

class UserViewHolder(val binding: UserItemBinding):RecyclerView.ViewHolder(binding.root){

    fun bind(userWithMessages: UserWithMessages, clickListener:(UserWithMessages)->Unit){
        binding.username.text = userWithMessages.owner.name
        binding.lastmessage.text = if(userWithMessages.messages.size > 0) userWithMessages.messages[userWithMessages.messages.size-1].message else ""
        binding.lastseen.text = if(userWithMessages.messages.size > 0) userWithMessages.messages[userWithMessages.messages.size-1].timestamp else ""
        Glide.with(binding.root).load(userWithMessages.owner.photo).circleCrop().into(binding.userphoto)
        binding.listItemLayout.setOnClickListener{
            clickListener(userWithMessages)
        }
    }
}