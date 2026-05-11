package com.example.aichat.presentation.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.aichat.domain.model.ChatMessage
import com.example.aichat.domain.model.MessageSender

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    viewModel: ChatViewModel,
    onUploadClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var inputText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AI Chat & RAG") },
                actions = {
                    Button(onClick = onUploadClick) {
                        Text("Upload PDF")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                reverseLayout = false
            ) {
                items(uiState.messages) { message ->
                    ChatBubble(message)
                }
                if (uiState.isLoading) {
                    item {
                        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                    }
                }
            }

            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Type a message...") }
                )
                IconButton(
                    onClick = {
                        viewModel.sendMessage(inputText)
                        inputText = ""
                    },
                    enabled = !uiState.isLoading && inputText.isNotBlank()
                ) {
                    Icon(Icons.Default.Send, contentDescription = "Send")
                }
            }
        }
    }
}

@Composable
fun ChatBubble(message: ChatMessage) {
    val alignment = if (message.sender == MessageSender.USER) Alignment.CenterEnd else Alignment.CenterStart
    val color = if (message.sender == MessageSender.USER) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.secondaryContainer
    val shape = if (message.sender == MessageSender.USER) 
        RoundedCornerShape(16.dp, 16.dp, 0.dp, 16.dp) 
    else 
        RoundedCornerShape(16.dp, 16.dp, 16.dp, 0.dp)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        contentAlignment = alignment
    ) {
        Surface(
            color = color,
            shape = shape,
            tonalElevation = 4.dp
        ) {
            Text(
                text = message.text,
                modifier = Modifier.padding(12.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
