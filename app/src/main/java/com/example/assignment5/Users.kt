package com.example.assignment5

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
data class Users(
    @PrimaryKey(autoGenerate = true)
    var itemId: Long = 0L,

    @ColumnInfo(name = "mobile_no")
    val MobileNo: String,

    @ColumnInfo(name = "password")
    val Password: String,
)
