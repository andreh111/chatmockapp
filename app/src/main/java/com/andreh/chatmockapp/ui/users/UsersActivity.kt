package com.andreh.chatmockapp.ui.users

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.andreh.chatmockapp.R
import com.andreh.chatmockapp.data.db.User
import com.andreh.chatmockapp.data.db.UserDatabase
import com.andreh.chatmockapp.data.repositories.UserRepository
import com.andreh.chatmockapp.databinding.ActivityUsersBinding
import com.andreh.chatmockapp.ui.chat.ChatActivity
import com.andreh.chatmockapp.utils.randomString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
            if (repository.getNumUsers()==0){
                for (i in 1..200) {
                    repository.insert(User(0, ('a'..'z').randomString(8), 0))
                }
            }
        }
        initRecyclerView()

    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter =
            UsersAdapter({ selectedItem: User -> listItemClicked(selectedItem) })
        binding.recyclerView.adapter = adapter

        displaySubscribersList()
    }


    private fun displaySubscribersList() {
        usersViewModel.users.observe(this, Observer {
            Log.i("MYTAG", it.toString())
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun listItemClicked(user: User) {
        val intent = Intent(this, ChatActivity::class.java).apply {
            putExtra("user", user.name)
        }
        startActivity(intent)
    }
}