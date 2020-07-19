package com.andreh.chatmockapp.ui.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andreh.chatmockapp.data.repositories.UserRepository
import java.lang.IllegalArgumentException

class UsersViewModelFactory(private val repository: UserRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UsersViewModel::class.java)){
            return UsersViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }

}