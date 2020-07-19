package com.andreh.chatmockapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.andreh.chatmockapp.data.db.messages.MessageDAO
import com.andreh.chatmockapp.data.db.users.User
import com.andreh.chatmockapp.data.db.users.UserDAO
import com.andreh.chatmockapp.data.db.messages.Message
@Database(entities = arrayOf(User::class, Message::class), version = 1)
abstract class UserDatabase : RoomDatabase() {

    abstract val subscriberDAO : UserDAO
    abstract val messageDAO: MessageDAO

    companion object{
        @Volatile
        private var INSTANCE : UserDatabase? = null

        fun getInstance(context: Context):UserDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance==null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        "user_data_database"
                    )
                    .build()
                }
                return instance
            }
        }

    }


}