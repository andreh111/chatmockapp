package com.andreh.chatmockapp.ui.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.andreh.chatmockapp.R
import com.andreh.chatmockapp.data.db.UserDatabase
import com.andreh.chatmockapp.data.db.messages.Message
import com.andreh.chatmockapp.data.db.users.User
import com.andreh.chatmockapp.data.repositories.MessageRepository
import com.andreh.chatmockapp.databinding.ActivityChatBinding
import com.andreh.chatmockapp.ui.users.UsersAdapter
import com.andreh.chatmockapp.ui.users.UsersViewModel
import com.andreh.chatmockapp.ui.users.UsersViewModelFactory
import com.andreh.chatmockapp.utils.randomString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var chatsViewModel: ChatsViewModel
    private lateinit var chatRecyclerViewAdapter: ChatRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat)

        val user = intent.getStringExtra("user")
        val userId = intent.getIntExtra("userId", 0)

        if (user != null) {
            setTitle(user)
        }

        val dao = UserDatabase.getInstance(application).messageDAO
        val repository = MessageRepository(dao, userId)
        val factory = ChatsViewModelFactory(repository)
        chatsViewModel = ViewModelProvider(this, factory).get(ChatsViewModel::class.java)
        binding.chatViewModel = chatsViewModel
        binding.lifecycleOwner = this
        GlobalScope.launch(Dispatchers.Main) {
            //generate 200 users if users table is empty

            for (i in 1..5) {
                repository.insert(
                    Message(
                        0,
                        ('a'..'z').randomString(20),
                        "2020/2/20",
                        1
                    )
                )
            }
        }


        initRecyclerView()
    }

    //initializing the recycler view
    private fun initRecyclerView() {
        binding.chatRecyclerview.layoutManager = LinearLayoutManager(this)
        chatRecyclerViewAdapter = ChatRecyclerViewAdapter()
        binding.chatRecyclerview.adapter = chatRecyclerViewAdapter

        displayUserMessagesList()
    }

    private fun displayUserMessagesList() {
        chatsViewModel.messages.observe(this, Observer {
            chatRecyclerViewAdapter.setList(it)
            chatRecyclerViewAdapter.notifyDataSetChanged()
        })
    }
}