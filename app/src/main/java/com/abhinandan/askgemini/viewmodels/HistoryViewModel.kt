package com.abhinandan.askgemini.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.abhinandan.askgemini.room.Chat
import com.abhinandan.askgemini.room.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val historyRepository: ChatRepository): ViewModel()
{
    init {
        getAllChats()
    }

    fun getAllChats() {
        historyRepository.getAllChats()
    }

    fun sortAllChatById() {
        historyRepository.sortAllChatById()
    }

    fun deleteChat(chat: Chat) {
        historyRepository.deleteChat(chat = chat)
        getAllChats()
    }

    fun deleteAllChat() {
        historyRepository.deleteAllChats()
        getAllChats()
    }

    val historyList: LiveData<List<Chat>> = historyRepository.chatHistory
}