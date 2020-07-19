package com.andreh.chatmockapp.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.andreh.chatmockapp.data.db.messages.Message
import com.andreh.chatmockapp.databinding.ChatitemLeftBinding
import com.andreh.chatmockapp.databinding.ChatitemRightBinding

class ChatRecyclerViewAdapter()
    : RecyclerView.Adapter<ChatViewHolder>()
{
    private val messagesList = ArrayList<Message>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : ChatitemRightBinding =
            DataBindingUtil.inflate(layoutInflater, com.andreh.chatmockapp.R.layout.chatitem_right,parent,false)
        return ChatViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return messagesList.size
    }


    fun setList(messages: List<Message>) {
        messagesList.clear()
        messagesList.addAll(messages)

    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val item = messagesList.get(holder.adapterPosition)
        holder.bind(messagesList[position])
    }

}

class ChatViewHolder(val binding: ChatitemRightBinding):RecyclerView.ViewHolder(binding.root){

    fun bind(message: Message){
        binding.chatItemRightText.text = message.message
    }
}