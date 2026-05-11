package com.example.aichat.domain.repository

import com.example.aichat.domain.model.ChatMessage
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun getChatHistory(): Flow<List<ChatMessage>>
    suspend fun saveMessage(message: ChatMessage)
    suspend fun getAiResponse(prompt: String): String
    suspend fun processPdf(text: String)
    suspend fun queryPdf(query: String): String
}
