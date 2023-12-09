package com.android.gotripmap.domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class Profile(
  val id: Int = 0,
  val username: String = "John Doe",
  val phone: String = "8800553535",
  val email: String = "",
  val receiveEmails: Boolean = false,
  val photo: String = ""
)
