package com.andreh.chatmockapp.data.repositories
import com.andreh.chatmockapp.data.db.messages.Message
import com.andreh.chatmockapp.data.db.messages.MessageDAO

class MessageRepository(private val dao: MessageDAO, private val userId: Int) {

    val messages = dao.getAllMessages()

    suspend fun insert(message: Message):Long{
        return dao.insertMessage(message)
    }
}