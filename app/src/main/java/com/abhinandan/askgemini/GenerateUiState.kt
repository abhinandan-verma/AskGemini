package com.abhinandan.askgemini

sealed interface GenerateUiState {

    data object Initial : GenerateUiState

    data object Loading : GenerateUiState

    data class Success(
        val outputText: String
    ) : GenerateUiState

    data class Error(
        val errorMessage: String
    ) : GenerateUiState
}