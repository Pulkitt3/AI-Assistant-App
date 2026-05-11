# Android AI Chatbot with RAG

A powerful native Android application integrating Gemini AI for real-time chat and RAG (Retrieval-Augmented Generation) with PDF support.

## 🚀 Features
- **AI Chatbot**: Real-time text generation using Google's Gemini API.
- **RAG Implementation**: Upload PDFs and query their content using AI.
- **Chat History**: Persistent storage of conversations using Room database.
- **Modern UI**: Built entirely with Jetpack Compose following Material 3 guidelines.
- **Clean Architecture**: Organized into Data, Domain, and Presentation layers for maintainability.
- **MVVM + Flow**: Reactive programming model with Kotlin Flow and ViewModels.

## 🛠️ Built With
- **Kotlin**: Primary language.
- **Jetpack Compose**: Modern UI toolkit.
- **Room**: SQLite-based persistence.
- **Gemini Android SDK**: AI model integration.
- **PDFBox Android**: Text extraction from PDF files.
- **Coroutines & Flow**: Asynchronous programming.

## 🏗️ Architecture
The project follows **Clean Architecture** principles:
- **Domain Layer**: Contains business logic, UseCases, and Domain entities.
- **Data Layer**: Repositories, Database (Room), and API clients (Gemini).
- **Presentation Layer**: UI Components, Screens, and ViewModels.

## 📝 Setup
1. Clone the repository.
2. Get a Gemini API Key from [Google AI Studio](https://aistudio.google.com/app/apikey).
3. Add your API key to `local.properties` or set it in the `GeminiClient` class.
4. Build and run the app in Android Studio.

## 🔐 Security
- **Encryption**: Chat messages are stored in a SQLite database. For production, consider using **SQLCipher** to encrypt the database file.
- **Secure Storage**: Sensitive information like API keys is managed via `EncryptedSharedPreferences` through the `SecurityManager`.
- **SSL Pinning**: For custom backend integrations, use `OkHttp` with `CertificatePinner`. Google's Gemini SDK handles secure communication internally with Google services.
- **Memory Safety**: Use `viewModelScope` and `collectAsStateWithLifecycle` to prevent memory leaks and ensure UI updates are tied to the lifecycle.
- **ANR Prevention**: All heavy operations (PDF parsing, Database I/O, Network calls) are explicitly moved to `Dispatchers.IO`.

## 📄 License
This project is licensed under the Apache 2.0 License.
