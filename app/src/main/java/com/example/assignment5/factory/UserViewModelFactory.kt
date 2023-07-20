package com.example.assignment5.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.assignment5.repository.Repository
import com.example.assignment5.vm.UserViewModel

class UserViewModelFactory(val rep : Repository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModel(rep) as T
    }
}