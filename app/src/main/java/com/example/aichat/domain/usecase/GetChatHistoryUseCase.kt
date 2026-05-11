package com.example.aichat.domain.usecase

import com.example.aichat.domain.model.ChatMessage
import com.example.aichat.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow

class GetChatHistoryUseCase(private val repository: ChatRepository) {
    operator fun invoke(): Flow<List<ChatMessage>> = repository.getChatHistory()
}
