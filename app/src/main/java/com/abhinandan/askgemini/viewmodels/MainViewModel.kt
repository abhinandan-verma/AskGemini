package com.abhinandan.askgemini.viewmodels

import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhinandan.askgemini.BuildConfig
import com.abhinandan.askgemini.GenerateUiState
import com.abhinandan.askgemini.room.Chat
import com.abhinandan.askgemini.room.ChatDao
import com.abhinandan.askgemini.utils.MainScreenState
import com.google.ai.client.generativeai.GenerativeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val mainDao: ChatDao) : ViewModel() {


    private val _allChats = MutableStateFlow<List<Chat>>(emptyList())
    val allChats: StateFlow<List<Chat>> = _allChats
    val _lChats: MutableLiveData<List<Chat>> = MutableLiveData()

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

    fun getAllChats() {
        viewModelScope.launch(Dispatchers.IO) {
            _allChats.value = mainDao.getAllChats()
        }
    }

    private val _generativeUiState = MutableStateFlow<GenerateUiState>(GenerateUiState.Initial)
    val generativeUiState: StateFlow<GenerateUiState> = _generativeUiState

    private val generativeModel = GenerativeModel(
        modelName = "gemini-pro",
        apiKey = BuildConfig.apiKey
    )
    fun generateResponse(prompt: String, time: Long) {
        _generativeUiState.value = GenerateUiState.Loading

        viewModelScope.launch(Dispatchers.IO) {

            val response = generativeModel.generateContent(prompt)

            if (response.text != null && response.text!!.isNotEmpty()) {
                _generativeUiState.value = GenerateUiState.Success(response.text!!)
                upsertChat(
                    Chat(
                        id = 0,
                        prompt = response.text!!,
                        fromUser = false,
                        timeStamp =  System.currentTimeMillis()
                    ),
                    time = time
                )

            } else {
                _generativeUiState.value = GenerateUiState.Error("Error generating response")
            }


        }

    }


        // Speech to text
        var state by mutableStateOf(MainScreenState())
            private set

        fun changeTextValue(text:String){
            viewModelScope.launch {
                state = state.copy(
                    text = text
                )
            }
        }
    }
