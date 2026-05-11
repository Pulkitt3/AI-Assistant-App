package com.example.aichat.domain.usecase

import com.example.aichat.domain.model.ChatMessage
import com.example.aichat.domain.model.MessageSender
import com.example.aichat.domain.repository.ChatRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.*

@OptIn(ExperimentalCoroutinesApi::class)
class SendMessageUseCaseTest {

    @Mock
    private lateinit var repository: ChatRepository

    private lateinit var sendMessageUseCase: SendMessageUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        sendMessageUseCase = SendMessageUseCase(repository)
    }

    @Test
    fun `invoke should save user message and ai response`() = runTest {
        // Given
        val text = "Hello"
        val expectedAiResponse = "Hi there!"
        `when`(repository.getAiResponse(text)).thenReturn(expectedAiResponse)

        // When
        val result = sendMessageUseCase(text)

        // Then
        assertEquals(expectedAiResponse, result)
        
        // Verify user message was saved
        verify(repository).saveMessage(argThat { 
            this.text == text && this.sender == MessageSender.USER 
        })
        
        // Verify AI message was saved
        verify(repository).saveMessage(argThat { 
            this.text == expectedAiResponse && this.sender == MessageSender.AI 
        })
    }
}
