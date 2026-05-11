package com.example.aichat.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "chat_messages")
data class ChatMessageEntity(
    @PrimaryKey val id: String,
    val text: String,
    val sender: String,
    val timestamp: Long
)

@Dao
interface ChatDao {
    @Query("SELECT * FROM chat_messages ORDER BY timestamp ASC")
    fun getAllMessages(): Flow<List<ChatMessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: ChatMessageEntity)

    @Query("DELETE FROM chat_messages")
    suspend fun deleteAllMessages()
}

@Database(entities = [ChatMessageEntity::class], version = 1)
abstract class ChatDatabase : RoomDatabase() {
    abstract fun chatDao(): ChatDao
}
