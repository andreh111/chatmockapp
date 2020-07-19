package com.andreh.chatmockapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDAO {
    @Insert
    suspend fun insertUser(user: User): Long

    @Query("DELETE FROM USERS_DATA_TABLE")
    suspend fun deleteAll() : Int

    @Query("SELECT * FROM users_data_table")
    fun getAllUsers(): LiveData<List<User>>

    @Query("SELECT COUNT(*) FROM users_data_table")
    suspend fun countUsers(): Int

}