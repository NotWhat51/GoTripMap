package com.android.gotripmap.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
//data class для базы данных Room, хранит сведения об истории запросов
data class SearchEntryDbModel(
  @PrimaryKey(autoGenerate = true) val id: Int = 0,
  @ColumnInfo val entry: String,
  @ColumnInfo val dateTime: LocalDateTime
)

