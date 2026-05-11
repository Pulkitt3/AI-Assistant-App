package com.example.aichat.domain.usecase

import com.example.aichat.domain.model.ChatMessage
import com.example.aichat.domain.model.MessageSender
import com.example.aichat.domain.repository.ChatRepository

class SendMessageUseCase(private val repository: ChatRepository) {
    suspend operator fun invoke(text: String): String {
        val userMessage = ChatMessage(text = text, sender = MessageSender.USER)
        repository.saveMessage(userMessage)
        
        val aiResponse = repository.getAiResponse(text)
        val aiMessage = ChatMessage(text = aiResponse, sender = MessageSender.AI)
        repository.saveMessage(aiMessage)
        
        return aiResponse
    }
}
