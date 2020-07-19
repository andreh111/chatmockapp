package com.andreh.chatmockapp.ui.users

import androidx.databinding.Observable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreh.chatmockapp.data.db.User
import com.andreh.chatmockapp.data.repositories.UserRepository
import kotlinx.coroutines.launch

class UsersViewModel(private val repository: UserRepository) : ViewModel(), Observable {

    val users = repository.users


    fun insert(user: User) = viewModelScope.launch {
        repository.insert(user)
    }



    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }


    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }


}