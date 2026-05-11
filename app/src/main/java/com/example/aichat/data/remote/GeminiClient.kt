package com.example.aichat.data.remote

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content

class GeminiClient(apiKey: String) {
    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = apiKey
    )

    suspend fun generateResponse(prompt: String): String {
        return try {
            val response = generativeModel.generateContent(prompt)
            response.text ?: "I am sorry, I couldn't generate a response."
        } catch (e: Exception) {
            "Error: ${e.message}"
        }
    }

    // Used for RAG: Generate embeddings for text chunks
    suspend fun generateEmbedding(text: String): List<Float> {
        // Note: Real Gemini Android SDK has an embedContent method
        // For simplicity in this demo, we use a placeholder or handle it via a different model
        // generativeModel.embedContent(text)
        return listOf(0f) // Placeholder
    }
}
