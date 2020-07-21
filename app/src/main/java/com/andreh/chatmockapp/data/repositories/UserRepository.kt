package com.andreh.chatmockapp.data.repositories

import com.andreh.chatmockapp.data.db.users.User
import com.andreh.chatmockapp.data.db.users.UserDAO


//User Repository
class UserRepository(private val dao : UserDAO) {

    val users = dao.getAllUsers()

    suspend fun insert(user: User):Long{
        return dao.insertUser(user)
    }

    suspend fun deleteAll() : Int{
        return dao.deleteAll()
    }

    suspend fun getNumUsers():Int{
        return dao.countUsers()
    }
}