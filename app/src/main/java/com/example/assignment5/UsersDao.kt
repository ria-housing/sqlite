package com.example.assignment5

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.DeleteTable
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UsersDao {

    @Insert
    suspend fun insert(item: Users)

    @Query("SELECT * FROM users")
    suspend fun getUsers(): List<Users>

    @Query("SELECT * FROM users where mobile_no = :itm")
    suspend fun getCustomUser(itm: String): List<Users>

    @Query("SELECT itemId FROM users where mobile_no = :itm")
    suspend fun getId(itm:String):Long
    @Update
    suspend fun update(item: Users)

    @Delete
    suspend fun delete(item: Users)

    @Query("DELETE FROM users")
    suspend fun deleteAll()
//    @De
//    @DeleteTable
//    suspend fun deleteall(item:String)
}