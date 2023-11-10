package com.android.gotripmap.domain.entities

data class Route(
  val id: Int,
  val length: String,
  val startPoint: Coordinate,
  val startPointSummary: String, //будет ли эти даннные выдавать сервер?
  val startPointShort: String,
  val endPoint: Coordinate,
  val endPointShort: String,
  val endPointSummary: String, //будет ли эти данные выдавать сервер?
  val imageLink: String,
  val timeRequired: String,
  val transport: Transport,
  val searchEntry: SearchEntry,
  val liked: Boolean = false
)
