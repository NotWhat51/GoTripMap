package com.android.gotripmap.domain.entities

data class Route(
  val id: Int = 0,
  val length: String,
  val startPoint: Coordinate,
  val startPointPlace: String, //будет ли эти даннные выдавать сервер?
  val startPointAddress: String,
  val endPoint: Coordinate,
  val endPointPlace: String,
  val endPointAddress: String, //будет ли эти данные выдавать сервер?
  val imageLink: String,
  val timeRequired: String,
  val transport: Transport,
  val searchEntry: Int,
  val liked: Boolean = false
)
