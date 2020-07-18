package com.andreh.chatmockapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    fun insertUser(user: User): Long

    @Query("SELECT * FROM users_data_table")
    fun getAllUsers(): Long
}