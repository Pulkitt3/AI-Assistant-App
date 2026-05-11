package com.example.aichat.presentation.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aichat.domain.model.ChatMessage
import com.example.aichat.domain.usecase.GetChatHistoryUseCase
import com.example.aichat.domain.usecase.ProcessPdfUseCase
import com.example.aichat.domain.usecase.SendMessageUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ChatViewModel(
    private val getChatHistoryUseCase: GetChatHistoryUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val processPdfUseCase: ProcessPdfUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getChatHistoryUseCase().collect { messages ->
                _uiState.update { it.copy(messages = messages) }
            }
        }
    }

    fun sendMessage(text: String) {
        if (text.isBlank()) return
        
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                sendMessageUseCase(text)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun onPdfProcessed(text: String) {
        viewModelScope.launch {
            processPdfUseCase(text)
        }
    }
}

data class ChatUiState(
    val messages: List<ChatMessage> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
