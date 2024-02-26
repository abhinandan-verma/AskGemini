package com.abhinandan.askgemini.utils

data class MainScreenState(
    var text: String? = null
)

sealed interface SpeechState {
    data object Idle : SpeechState
    data object Listening : SpeechState
    data class Error(val error: String) : SpeechState
    data class Result(val text: String) : SpeechState
}