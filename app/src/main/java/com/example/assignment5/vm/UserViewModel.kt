package com.example.assignment5.vm

import androidx.compose.runtime.derivedStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment5.model.Users
import com.example.assignment5.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class UserViewModel(val userRepo : Repository) : ViewModel(){
   fun insert(user: Users){
        viewModelScope.launch {
            try {
                userRepo.insert(Users(MobileNo = user.MobileNo, Password = user.Password))

            } catch (ex: Exception) {
                println("cancelled")
            }
        }
    }
    fun getCustomUser(mob : String):List<Users>{
        lateinit var lis:List<Users>
        derivedStateOf {
            runBlocking {
                withContext(Dispatchers.IO) {
                    lis = userRepo.getCustomUser(mob)
                }
            }
        }
        return lis
    }
}