package com.abhinandan.askgemini.room

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatRepository( private val chatDao: ChatDao) {

    val allChats = MutableLiveData<List<Chat>>()

    val chatHistory = MutableLiveData<List<Chat>>()

    val foundChat = MutableLiveData<Chat>()


    private val _chatsList = MutableStateFlow<List<Chat>>(emptyList())
    val chatsList: StateFlow<List<Chat>> = _chatsList

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insertChat(chat: Chat) {
        coroutineScope.launch(Dispatchers.IO) {
            chatDao.insert(chat)
            _chatsList.value = chatDao.getAllChats()
        }
    }

    fun deleteChat(chat: Chat) {
        coroutineScope.launch(Dispatchers.IO) {
            chatDao.delete(chat)
        }
    }

    fun upsertChat(chat: Chat) {
        coroutineScope.launch(Dispatchers.IO) {
            chatDao.upsert(chat)
            _chatsList.value = chatDao.getAllChats()
        }
    }

    fun getAllChats() {
        coroutineScope.launch(Dispatchers.IO) {
           chatHistory.postValue(chatDao.getAllChats())
            _chatsList.value = chatDao.getAllChats()
        }
    }

    fun getAllChatFromCurrent(time: Long){
        coroutineScope.launch(Dispatchers.IO) {
            allChats.postValue(chatDao.getAllChatsFromCurrent(time))
        }
    }

    fun sortAllChatById(){
        coroutineScope.launch(Dispatchers.IO) {
            allChats.postValue(chatDao.sortAllChatById())
        }
    }

    fun findChatById(chatId: Int){
        coroutineScope.launch(Dispatchers.IO) {
            foundChat.postValue(chatDao.findChatById(chatId = chatId))
        }
    }

    fun deleteAllChats() {
        coroutineScope.launch(Dispatchers.IO) {
            chatDao.deleteAllChats()
        }
    }
}