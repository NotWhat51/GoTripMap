package com.android.gotripmap.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.gotripmap.domain.entities.Transport
import java.time.LocalDateTime
/**
 * Представление запроса в базе данных Rooms
 */
@Entity
data class SearchEntryDbModel(
  @PrimaryKey(autoGenerate = true) val id: Int = 0,
  @ColumnInfo val entry: String,
  @ColumnInfo val dateTime: Long,
  @ColumnInfo val transport: Transport
)

