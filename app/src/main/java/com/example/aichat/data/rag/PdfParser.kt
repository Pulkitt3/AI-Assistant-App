package com.example.aichat.data.rag

import android.content.Context
import android.net.Uri
import com.tomroush.pdfbox.android.PDFBoxResourceLoader
import com.tomroush.pdfbox.pdmodel.PDDocument
import com.tomroush.pdfbox.text.PDFTextStripper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PdfParser(private val context: Context) {

    init {
        PDFBoxResourceLoader.init(context)
    }

    suspend fun extractTextFromUri(uri: Uri): String = withContext(Dispatchers.IO) {
        try {
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                val document = PDDocument.load(inputStream)
                val stripper = PDFTextStripper()
                val text = stripper.getText(document)
                document.close()
                text
            } ?: ""
        } catch (e: Exception) {
            "Failed to parse PDF: ${e.message}"
        }
    }
}
