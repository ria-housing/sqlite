package com.example.assignment5.repository

import com.example.assignment5.model.Users
import com.example.assignment5.db.UsersDatabase

class Repository(val usersdb: UsersDatabase) {
    suspend fun insert(user: Users) = usersdb.UsersDao().insert(user)
    suspend fun getUsers() = usersdb.UsersDao().getUsers()
    suspend fun getCustomUser(mob:String):List<Users> = usersdb.UsersDao().getCustomUser(mob)
}