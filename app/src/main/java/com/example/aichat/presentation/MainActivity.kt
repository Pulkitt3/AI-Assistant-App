package com.example.aichat.presentation

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.aichat.data.local.ChatDatabase
import com.example.aichat.data.rag.PdfParser
import com.example.aichat.data.remote.GeminiClient
import com.example.aichat.data.repository.ChatRepositoryImpl
import com.example.aichat.presentation.chat.ChatScreen
import com.example.aichat.presentation.chat.ChatViewModel
import com.example.aichat.presentation.theme.AIChatTheme
import kotlinx.coroutines.launch

import com.example.aichat.domain.usecase.GetChatHistoryUseCase
import com.example.aichat.domain.usecase.ProcessPdfUseCase
import com.example.aichat.domain.usecase.SendMessageUseCase

import com.example.aichat.data.security.SecurityManager
import kotlinx.coroutines.Dispatchers

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: ChatViewModel
    private lateinit var pdfParser: PdfParser
    private lateinit var securityManager: SecurityManager

    private val selectPdfLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            lifecycleScope.launch(Dispatchers.IO) {
                val text = pdfParser.extractTextFromUri(it)
                viewModel.onPdfProcessed(text)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        securityManager = SecurityManager(this)
        
        val db = Room.databaseBuilder(
            applicationContext,
            ChatDatabase::class.java, "chat-db"
        ).build()
        
        val geminiClient = GeminiClient(apiKey = securityManager.getApiKey() ?: "YOUR_GEMINI_API_KEY")
        val repository = ChatRepositoryImpl(db.chatDao(), geminiClient, securityManager)
        
        // Instantiate Use Cases
        val getChatHistoryUseCase = GetChatHistoryUseCase(repository)
        val sendMessageUseCase = SendMessageUseCase(repository)
        val processPdfUseCase = ProcessPdfUseCase(repository)
        
        viewModel = ChatViewModel(getChatHistoryUseCase, sendMessageUseCase, processPdfUseCase)
        pdfParser = PdfParser(this)

        setContent {
            AIChatTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ChatScreen(
                        viewModel = viewModel,
                        onUploadClick = { selectPdfLauncher.launch("application/pdf") }
                    )
                }
            }
        }
    }
}
