package com.andreh.chatmockapp.data.repositories
import androidx.lifecycle.LiveData
import com.andreh.chatmockapp.data.db.messages.Message
import com.andreh.chatmockapp.data.db.messages.MessageDAO

class MessageRepository(private val dao: MessageDAO, private val userId: Int) {

    val messages = dao.getAllMessages(userId)

    suspend fun insert(message: Message):Long{
        return dao.insertMessage(message)
    }

    suspend fun countMessages(): Int{
        return dao.countMessages()
    }

    suspend fun getAllMessages(userId: Int): LiveData<List<Message>>{
        return dao.getAllMessages(userId)
    }
}