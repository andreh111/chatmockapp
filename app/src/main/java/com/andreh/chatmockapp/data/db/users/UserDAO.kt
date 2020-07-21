package com.andreh.chatmockapp.data.db.users

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.andreh.chatmockapp.data.db.users.User

@Dao
interface UserDAO {
    @Insert
    suspend fun insertUser(user: User): Long

    @Query("DELETE FROM users_data_table")
    suspend fun deleteAll(): Int

    @Transaction
    @Query("SELECT * FROM users_data_table")
    fun getAllUsers(): LiveData<List<UserWithMessages>>

    @Query("SELECT COUNT(*) FROM users_data_table")
    suspend fun countUsers(): Int

}