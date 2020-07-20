package com.andreh.chatmockapp.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andreh.chatmockapp.data.repositories.MessageRepository
import java.lang.IllegalArgumentException

class ChatsViewModelFactory(private val repository: MessageRepository,private val userId:Int): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ChatsViewModel::class.java)){
            return ChatsViewModel(repository,userId) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }

}