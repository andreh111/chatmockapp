package com.andreh.chatmockapp.ui.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.andreh.chatmockapp.R

class ChatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val user = intent.getStringExtra("user")

        if (user!=null){
            setTitle(user)
        }
    }
}