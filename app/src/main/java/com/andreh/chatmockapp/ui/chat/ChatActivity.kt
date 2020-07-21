package com.andreh.chatmockapp.ui.chat

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.andreh.chatmockapp.R
import com.andreh.chatmockapp.data.db.UserDatabase
import com.andreh.chatmockapp.data.repositories.MessageRepository
import com.andreh.chatmockapp.databinding.ActivityChatBinding
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var chatsViewModel: ChatsViewModel
    private lateinit var chatRecyclerViewAdapter: ChatRecyclerViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat)
        val user = intent.getStringExtra("user")
        var userId = intent.getIntExtra("userId", 0)
        if (user != null) {
            setTitle(user)
        }
        val dao = UserDatabase.getInstance(application).messageDAO
        var repository = MessageRepository(dao, userId)
        val factory = ChatsViewModelFactory(repository,userId)
        chatsViewModel = ViewModelProvider(this, factory).get(ChatsViewModel::class.java)
        binding.chatViewModel = chatsViewModel
        binding.lifecycleOwner = this

        initRecyclerView()

        //Add a Listener so if press keyboard enter the message will be sent
        edittext_chatbox.setOnKeyListener(
            View.OnKeyListener { v, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                    //Perform Code
                    chatsViewModel.insert()
                    return@OnKeyListener true
                }
                false
            }
        )


    }

    //initializing the recycler view
    private fun initRecyclerView() {
        binding.chatRecyclerview.layoutManager = LinearLayoutManager(this)
        chatRecyclerViewAdapter = ChatRecyclerViewAdapter()
        binding.chatRecyclerview.adapter = chatRecyclerViewAdapter
        binding.chatRecyclerview.apply {
            layoutManager = LinearLayoutManager(context).apply {
                stackFromEnd = true
                reverseLayout = false
            }
        }
        displayUserMessagesList()

    }

    //function to display user messages list
    private fun displayUserMessagesList() {

        chatsViewModel.messages.observe(this, Observer {
            chatRecyclerViewAdapter.setList(it)
            binding.chatRecyclerview.smoothScrollToPosition(chatRecyclerViewAdapter.itemCount )

            chatRecyclerViewAdapter.notifyDataSetChanged()
        })
    }
}