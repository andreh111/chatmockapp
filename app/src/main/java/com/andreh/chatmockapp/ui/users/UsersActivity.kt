package com.andreh.chatmockapp.ui.users

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.andreh.chatmockapp.R
import com.andreh.chatmockapp.data.db.UserDatabase
import com.andreh.chatmockapp.data.db.users.User
import com.andreh.chatmockapp.data.db.users.UserWithMessages
import com.andreh.chatmockapp.data.repositories.UserRepository
import com.andreh.chatmockapp.databinding.ActivityUsersBinding
import com.andreh.chatmockapp.ui.chat.ChatActivity
import com.andreh.chatmockapp.utils.randomString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter


@Suppress("IMPLICIT_CAST_TO_ANY")
class UsersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUsersBinding
    private lateinit var usersViewModel: UsersViewModel
    private lateinit var adapter: UsersAdapter
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_users)
        val dao = UserDatabase.getInstance(application).subscriberDAO
        val repository = UserRepository(dao)
        val factory = UsersViewModelFactory(repository)
        usersViewModel = ViewModelProvider(this, factory).get(UsersViewModel::class.java)
        binding.viewModel = usersViewModel
        binding.lifecycleOwner = this


        //filling randomly 200 users when the app first starts it checks if the user table is empty it will fill it with random 200 users
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
    @RequiresApi(Build.VERSION_CODES.O)
    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )

        adapter =
            UsersAdapter({ selectedItem: UserWithMessages -> listItemClicked(selectedItem) })
        binding.recyclerView.adapter = adapter

        displaySubscribersList()
    }

    //displaying users list and sort them by timestamp
    @RequiresApi(Build.VERSION_CODES.O)
    private fun displaySubscribersList() {
        usersViewModel.users.observe(this, Observer {
            Log.d("mylist",it.toString())

            //order chats by timestamp
            adapter.setList(
                it.sortedByDescending {
                    it.messages.lastOrNull()?.timestamp
                }
            )
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