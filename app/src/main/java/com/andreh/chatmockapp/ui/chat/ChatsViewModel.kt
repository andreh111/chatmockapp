package com.andreh.chatmockapp.ui.chat

import androidx.databinding.Observable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreh.chatmockapp.data.db.messages.Message
import com.andreh.chatmockapp.data.repositories.MessageRepository
import kotlinx.coroutines.launch

class ChatsViewModel(private val repository: MessageRepository) : ViewModel(), Observable {

    val messages = repository.messages


    fun insert(message: Message) = viewModelScope.launch {
        repository.insert(message)
    }


    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }


}