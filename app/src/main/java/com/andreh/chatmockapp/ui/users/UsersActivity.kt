package com.andreh.chatmockapp.ui.users

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.andreh.chatmockapp.R
import com.andreh.chatmockapp.data.db.UserDatabase
import com.andreh.chatmockapp.data.db.users.User
import com.andreh.chatmockapp.data.db.users.UserWithMessages
import com.andreh.chatmockapp.data.repositories.UserRepository
import com.andreh.chatmockapp.databinding.ActivityUsersBinding
import com.andreh.chatmockapp.ui.chat.ChatActivity
import com.andreh.chatmockapp.utils.dateTimeStrToLocalDateTime
import com.andreh.chatmockapp.utils.randomString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.Objects.compare
import kotlin.Comparator


class UsersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUsersBinding
    private lateinit var usersViewModel: UsersViewModel
    private lateinit var adapter: UsersAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_users)
        val dao = UserDatabase.getInstance(application).subscriberDAO
        val repository = UserRepository(dao)
        val factory = UsersViewModelFactory(repository)
        usersViewModel = ViewModelProvider(this, factory).get(UsersViewModel::class.java)
        binding.viewModel = usersViewModel
        binding.lifecycleOwner = this
        GlobalScope.launch(Dispatchers.Main) {
            //generate 200 users if users table is empty
            if (repository.getNumUsers() == 0) {
                for (i in 1..200) {
                    repository.insert(
                        user = User(
                            id = 0,
                            name = ('a'..'z').randomString(8),
                            photo = 0
                        )
                    )
                }
            }
        }
        initRecyclerView()

    }

    //initializing the recycler view
    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter =
            UsersAdapter({ selectedItem: UserWithMessages -> listItemClicked(selectedItem) })
        binding.recyclerView.adapter = adapter

        displaySubscribersList()
    }

    //displaying users list and sort them by timestamp
    private fun displaySubscribersList() {
        usersViewModel.users.observe(this, Observer {
            it.map(dateTimeStrToLocalDateTime).sorted()

            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }

    //when clicking on a user, go to the chat screen
    private fun listItemClicked(user: UserWithMessages) {
        val intent = Intent(this, ChatActivity::class.java).apply {
            putExtra("user", user.owner.name)
            putExtra("userId", user.owner.id)
        }
        startActivity(intent)
    }
}