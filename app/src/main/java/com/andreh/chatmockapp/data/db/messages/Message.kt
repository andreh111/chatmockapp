package com.andreh.chatmockapp.data.db.messages

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages_data_table")
class Message(
    @PrimaryKey(autoGenerate = true)
    val messageid: Int,
    var message: String,
    var timestamp: String,
    @ColumnInfo(name="uid")
    var userId: Int
)