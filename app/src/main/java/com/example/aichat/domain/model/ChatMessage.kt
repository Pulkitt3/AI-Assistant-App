package com.example.aichat.domain.model

import java.util.UUID

data class ChatMessage(
    val id: String = UUID.randomUUID().toString(),
    val text: String,
    val sender: MessageSender,
    val timestamp: Long = System.currentTimeMillis()
)

enum class MessageSender {
    USER, AI
}
