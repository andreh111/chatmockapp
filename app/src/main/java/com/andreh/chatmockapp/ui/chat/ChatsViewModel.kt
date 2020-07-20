package com.andreh.chatmockapp.ui.chat

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreh.chatmockapp.data.db.messages.Message
import com.andreh.chatmockapp.data.repositories.MessageRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class ChatsViewModel(private val repository: MessageRepository) : ViewModel(), Observable {

    val messages = repository.messages


    @Bindable
    var inputMessage = MutableLiveData<String>()


    fun enterKeyBoard() = viewModelScope.launch {

    }

    fun insert() = viewModelScope.launch {
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        if (inputMessage!=null){
            var msg = Message(
                messageid = 0,
                message = inputMessage.value!!.trim(),
                timestamp = "${currentDate}",
                userId = 1
            )
            inputMessage.value = null
            repository.insert(msg)
        }

    }



    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }


}