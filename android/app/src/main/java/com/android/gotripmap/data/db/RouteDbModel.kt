package com.android.gotripmap.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.android.gotripmap.domain.entities.Coordinate
import com.android.gotripmap.domain.entities.SearchEntry
import com.android.gotripmap.domain.entities.Transport

@Entity(
  foreignKeys =
  [ForeignKey(
    onDelete = ForeignKey.CASCADE, entity = SearchEntryDbModel::class,
    parentColumns = arrayOf("id"), childColumns = arrayOf("searchEntry")
  )
  ]
)
//data class для базы данных Room, хранит сведения о маршрутах
data class RouteDbModel(
  @PrimaryKey(autoGenerate = true)  val id: Int,
  @ColumnInfo val length: String,
  @ColumnInfo val startPoint: Coordinate,
  @ColumnInfo val startPointSummary: String, //будет ли эти даннные выдавать сервер?
  @ColumnInfo val startPointShort: String,
  @ColumnInfo val endPoint: Coordinate,
  @ColumnInfo val endPointShort: String,
  @ColumnInfo val endPointSummary: String, //будет ли эти данные выдавать сервер?
  @ColumnInfo val imageLink: String,
  @ColumnInfo val timeRequired: String,
  @ColumnInfo val transport: Transport,
  @ColumnInfo val searchEntry: SearchEntry,
  @ColumnInfo val liked: Boolean = false
)
