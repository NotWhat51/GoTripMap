package com.android.gotripmap.data.pojo

import androidx.room.ColumnInfo
import com.android.gotripmap.domain.entities.Transport

data class RouteResponse(
  val length: String,
  val startPointX: Double,
  val startPointY: Double,
  val startPointPlace: String,
  val startPointAddress: String,
  val endPointX: Double,
  val endPointY: Double,
  val endPointAddress: String,
  val endPointPlace: String,
  val imageLink: String,
  val timeRequired: String
)
