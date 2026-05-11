package com.example.aichat.data.repository

import com.example.aichat.data.local.ChatDao
import com.example.aichat.data.local.ChatMessageEntity
import com.example.aichat.data.remote.GeminiClient
import com.example.aichat.domain.model.ChatMessage
import com.example.aichat.domain.model.MessageSender
import com.example.aichat.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

import com.example.aichat.data.security.SecurityManager

class ChatRepositoryImpl(
    private val chatDao: ChatDao,
    private val geminiClient: GeminiClient,
    private val securityManager: SecurityManager
) : ChatRepository {

    private var pdfContext: String = ""

    override fun getChatHistory(): Flow<List<ChatMessage>> {
        // Observes database: UI automatically updates when new data is inserted
        return chatDao.getAllMessages().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override suspend fun saveMessage(message: ChatMessage) {
        chatDao.insertMessage(message.toEntity())
    }

    override suspend fun getAiResponse(prompt: String): String {
        return try {
            val finalPrompt = if (pdfContext.isNotEmpty()) {
                "Context from PDF: $pdfContext\n\nQuestion: $prompt"
            } else {
                prompt
            }
            geminiClient.generateResponse(finalPrompt)
        } catch (e: Exception) {
            "Offline mode: Could not reach AI server. ${e.message}"
        }
    }

    override suspend fun processPdf(text: String) {
        // In a real RAG, we would chunk and embed. 
        // Here we store the first 2000 chars as context for simplicity.
        pdfContext = text.take(5000)
    }

    override suspend fun queryPdf(query: String): String {
        return getAiResponse(query)
    }

    private fun ChatMessageEntity.toDomainModel() = ChatMessage(
        id = id,
        text = text,
        sender = MessageSender.valueOf(sender),
        timestamp = timestamp
    )

    private fun ChatMessage.toEntity() = ChatMessageEntity(
        id = id,
        text = text,
        sender = sender.name,
        timestamp = timestamp
    )
}
