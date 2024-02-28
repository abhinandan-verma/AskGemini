package com.abhinandan.askgemini.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhinandan.askgemini.room.Chat
import com.abhinandan.askgemini.room.ChatDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainDao: ChatDao) : ViewModel() {


    private val _allChats = MutableStateFlow<List<Chat>>(emptyList())
    val allChats: StateFlow<List<Chat>> = _allChats

    fun getAllChatsFromCurrent(time: Long): List<Chat> {
        viewModelScope.launch(Dispatchers.IO) {
           _allChats.value =  mainDao.getAllChatsFromCurrent(time)
        }
        return _allChats.value
    }

    fun upsertChat(chat: Chat, time: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            mainDao.upsert(chat)
            _allChats.value = getAllChatsFromCurrent(time)
        }
    }

    fun deleteChat(chat: Chat) {
        viewModelScope.launch(Dispatchers.IO) {
            mainDao.delete(chat)
        }
    }
}