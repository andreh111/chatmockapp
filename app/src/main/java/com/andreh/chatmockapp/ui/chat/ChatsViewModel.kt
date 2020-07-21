package com.andreh.chatmockapp.ui.chat

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreh.chatmockapp.data.db.messages.Message
import com.andreh.chatmockapp.data.repositories.MessageRepository
import com.andreh.chatmockapp.utils.randomDelay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class ChatsViewModel(private val repository: MessageRepository,private val userId: Int) : ViewModel(), Observable {

    val messages = repository.messages

    @Bindable
    var inputMessage = MutableLiveData<String>()


    fun insert() = viewModelScope.launch {
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        if (inputMessage!=null){
            var msg = Message(
                messageid = 0,
                message = inputMessage.value!!.trim(),
                timestamp = "${currentDate}",
                userId = userId
            )
            //if send message , send it again twice after a randomized delay of 0.5
            //using randomDelay() method in utils package
            if(repository.insert(msg) > 0){

                randomDelay(0.0f,0.5f)
                var msg1 = Message(
                    messageid = 0,
                    message = inputMessage.value!!.trim(),
                    timestamp = "${sdf.format(Date())}",
                    userId = userId
                )
                repository.insert(msg1)
                randomDelay(0.0f,0.5f)
                var msg2 = Message(
                    messageid = 0,
                    message = inputMessage.value!!.trim(),
                    timestamp = "${sdf.format(Date())}",
                    userId = userId
                )
                repository.insert(msg2)
            }
            inputMessage.value = null

        }

    }



    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }


}