package com.example.aichat.domain.usecase

import com.example.aichat.domain.model.ChatMessage
import com.example.aichat.domain.model.MessageSender
import com.example.aichat.domain.repository.ChatRepository

class ProcessPdfUseCase(private val repository: ChatRepository) {
    suspend operator fun invoke(text: String) {
        repository.processPdf(text)
        val confirmationMessage = ChatMessage(
            text = "PDF processed successfully. You can now ask questions about its content.",
            sender = MessageSender.AI
        )
        repository.saveMessage(confirmationMessage)
    }
}
