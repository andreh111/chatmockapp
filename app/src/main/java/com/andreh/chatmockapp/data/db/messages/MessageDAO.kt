package com.andreh.chatmockapp.data.db.messages

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.TypeConverter

@Dao
interface MessageDAO {
    @Insert
    suspend fun insertMessage(message: Message): Long

    @Query("SELECT * FROM messages_data_table WHERE uid=:userId")
    fun getAllMessages(userId: Int): LiveData<List<Message>>

    @Query("SELECT COUNT(*) FROM messages_data_table")
    suspend fun countMessages(): Int


}