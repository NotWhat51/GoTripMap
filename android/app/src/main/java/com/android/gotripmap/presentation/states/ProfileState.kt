package com.android.gotripmap.presentation.states

import androidx.compose.ui.text.input.TextFieldValue
import com.android.gotripmap.domain.entities.Profile

data class ProfileState(
  val id: Int,
  var username: TextFieldValue,
  var phone: TextFieldValue,
  var email: TextFieldValue,
  var receiveEmails: Boolean = false,
  var photo: String = ""
) {
  constructor (profile: Profile) : this(
    profile.id,
    TextFieldValue(text = profile.username),
    TextFieldValue(text = profile.phone),
    TextFieldValue(text = profile.email),
    profile.receiveEmails,
    profile.photo
  )

  fun toProfile(): Profile = Profile(
    this.id,
    this.username.text,
    this.phone.text,
    this.email.text,
    this.receiveEmails,
    this.photo
  )
}
