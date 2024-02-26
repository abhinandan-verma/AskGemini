package com.abhinandan.askgemini.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_table")
data class Chat(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val prompt: String,
    val fromUser: Boolean,
    val timeStamp: Long = System.currentTimeMillis()
)
