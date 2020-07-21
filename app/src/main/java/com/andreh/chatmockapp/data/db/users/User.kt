package com.andreh.chatmockapp.data.db.users

import androidx.room.*
import com.andreh.chatmockapp.data.db.messages.Message

@Entity(tableName = "users_data_table")

data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userId")
    var id: Int,
    var name: String,
    var photo: Int
)

data class UserWithMessages(
    @Embedded val owner: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "uid"
    )
    val messages: List<Message>
)


