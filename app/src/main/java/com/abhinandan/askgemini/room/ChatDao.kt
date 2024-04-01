package com.abhinandan.askgemini.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface ChatDao {

    @Upsert
    suspend fun upsert(chat: Chat)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(chat: Chat)

    @Delete
    suspend fun delete(chat: Chat)

    @Query("SELECT * FROM chat_table")
    suspend fun getAllChats(): List<Chat>

    @Query("SELECT * FROM chat_table WHERE id = :chatId")
    suspend fun findChatById(chatId: Int): Chat

    @Query("DELETE  FROM chat_table")
    suspend fun deleteAllChats()

    @Query("SELECT * FROM chat_table ORDER BY id")
    suspend fun sortAllChatById(): List<Chat>

    @Query("SELECT * FROM chat_table WHERE timeStamp >= :time")
    suspend fun getAllChatsFromCurrent(time: Long): List<Chat>

    @Query("SELECT * FROM chat_table ORDER BY timeStamp DESC")
    suspend fun getAllMessages(): List<Chat>

}
