package com.andreh.chatmockapp.data.db.users

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_data_table")
class User (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="userId")
    var id: Int,
    var name: String,
    var photo: Int
)

