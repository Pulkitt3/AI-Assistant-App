package com.example.aichat.presentation.chat

import app.cash.turbine.test
import com.example.aichat.domain.model.ChatMessage
import com.example.aichat.domain.model.MessageSender
import com.example.aichat.domain.usecase.GetChatHistoryUseCase
import com.example.aichat.domain.usecase.ProcessPdfUseCase
import com.example.aichat.domain.usecase.SendMessageUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

@OptIn(ExperimentalCoroutinesApi::class)
class ChatViewModelTest {

    @Mock lateinit var getChatHistoryUseCase: GetChatHistoryUseCase
    @Mock lateinit var sendMessageUseCase: SendMessageUseCase
    @Mock lateinit var processPdfUseCase: ProcessPdfUseCase

    private lateinit var viewModel: ChatViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        
        `when`(getChatHistoryUseCase()).thenReturn(flowOf(emptyList()))
        viewModel = ChatViewModel(getChatHistoryUseCase, sendMessageUseCase, processPdfUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `sendMessage should update loading state`() = runTest {
        val text = "test message"
        
        viewModel.uiState.test {
            // Initial state
            assertEquals(false, awaitItem().isLoading)
            
            viewModel.sendMessage(text)
            
            // Should show loading then stop
            assertEquals(true, awaitItem().isLoading)
            assertEquals(false, awaitItem().isLoading)
            
            verify(sendMessageUseCase).invoke(text)
        }
    }

    @Test
    fun `onPdfProcessed should call processPdfUseCase`() = runTest {
        val text = "pdf content"
        viewModel.onPdfProcessed(text)
        verify(processPdfUseCase).invoke(text)
    }
}
