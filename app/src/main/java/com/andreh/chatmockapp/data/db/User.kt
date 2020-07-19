package com.andreh.chatmockapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_data_table")
class User (
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var name: String,
    var photo: Int
)

