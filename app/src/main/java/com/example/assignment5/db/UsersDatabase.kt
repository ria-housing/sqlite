package com.example.assignment5.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.assignment5.model.Users


@Database(entities = [Users::class], version = 1)
abstract class UsersDatabase : RoomDatabase() {
    abstract fun UsersDao(): UsersDao
    companion object {
        private var INSTANCE: UsersDatabase? = null
        fun getInstance(context: Context): UsersDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UsersDatabase::class.java,
                        "programtest.db"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}