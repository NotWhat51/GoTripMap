package com.android.gotripmap.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.android.gotripmap.domain.entities.Coordinate
import com.android.gotripmap.domain.entities.SearchEntry
import com.android.gotripmap.domain.entities.Transport

/**
 * Представление маршрута в базе данных Rooms
 */
@Entity
//data class для базы данных Room, хранит сведения о маршрутах
data class RouteDbModel(
  @PrimaryKey(autoGenerate = true)  val id: Int=0,
  @ColumnInfo val length: String,
  @ColumnInfo val startPointX: Double,
  @ColumnInfo val startPointY: Double,
  @ColumnInfo val startPointPlace: String,
  @ColumnInfo val startPointAddress: String,
  @ColumnInfo val endPointX: Double,
  @ColumnInfo val endPointY: Double,
  @ColumnInfo val endPointAddress: String,
  @ColumnInfo val endPointPlace: String,
  @ColumnInfo val imageLink: String,
  @ColumnInfo val timeRequired: String,
  @ColumnInfo val transport: Transport,
  @ColumnInfo val searchEntry: Int,
  @ColumnInfo val liked: Boolean = false
)
