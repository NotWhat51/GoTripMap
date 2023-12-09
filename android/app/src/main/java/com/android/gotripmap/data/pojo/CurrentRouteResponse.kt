package com.android.gotripmap.data.pojo

data class CurrentRouteResponse(val id: String, val entries: List<Entry>)

data class Entry(val entryno: Int,val tsys: String,val time: Int,val startpoint: String,val destpoint: DestPoint)

data class DestPoint(
  val name: String,
  val category: String
)
