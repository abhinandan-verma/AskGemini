package com.abhinandan.askgemini.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhinandan.askgemini.BuildConfig
import com.abhinandan.askgemini.GenerateUiState
import com.abhinandan.askgemini.room.Chat
import com.abhinandan.askgemini.room.ChatRepository
import com.abhinandan.askgemini.utils.MainScreenState
import com.abhinandan.askgemini.utils.SpeechState
import com.google.ai.client.generativeai.GenerativeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ChatViewModel @Inject constructor(private val chatRepository: ChatRepository): ViewModel() {



    init {
        chatRepository.getAllChats()
    }

    fun getAllChats() {
        chatRepository.getAllChats()
    }

    fun getAllChatsFromCurrent(time: Long) {
        chatRepository.getAllChatFromCurrent(time = time)
    }

    fun sortAllChatById() {
        chatRepository.sortAllChatById()
    }

    fun insertChat(chat: Chat) {
        chatRepository.insertChat(chat = chat)
        sortAllChatById()
    }

    fun deleteChat(chat: Chat) {
        chatRepository.deleteChat(chat = chat)
        getAllChats()
    }

    fun findChatById(chatId: Int) {
        chatRepository.findChatById(chatId = chatId)
    }

    fun upsertChat(chat: Chat) {
        chatRepository.upsertChat(chat = chat)
        getAllChatsFromCurrent(chat.timeStamp)
    }

    val chatList: LiveData<List<Chat>> = chatRepository.allChats
    val messageList : Flow<List<Chat>> = chatRepository.chatsList

    private val _uiState: MutableStateFlow<GenerateUiState> =
        MutableStateFlow(GenerateUiState.Initial)
    val gUiState: StateFlow<GenerateUiState> =
        _uiState.asStateFlow()

    private val generativeModel = GenerativeModel(
        modelName = "gemini-pro",
        apiKey = BuildConfig.apiKey
    )


    fun generateResponse(inputText: String) {

        _uiState.value = GenerateUiState.Loading

        viewModelScope.launch {
            try {
                val response = generativeModel.generateContent(inputText)

                if(response.text != null && response.text!!.isNotEmpty()){
                    _uiState.value = GenerateUiState.Success(response.text!!)
                    val chat = Chat(
                        id = 0,
                        prompt = response.text!!,
                        fromUser = false,
                        timeStamp = (System.currentTimeMillis())
                    )
                    insertChat(chat)
                   chatRepository.getAllChats()
                }
                if (response.text == null) {
                    _uiState.value = GenerateUiState.Error("No response found. Please try again.")
                }
            } catch (e: Exception) {
                _uiState.value = GenerateUiState.Error(e.localizedMessage ?: "")
            }
        }
    }

   var state by mutableStateOf(MainScreenState())

    private val _speechState : MutableStateFlow<SpeechState>
    = MutableStateFlow(SpeechState.Idle)
    val speechState : StateFlow<SpeechState> = _speechState

    fun changeTextValue(text: String) {
        viewModelScope.launch {
            state = state.copy(
                text = text
            )
        }
    }
}