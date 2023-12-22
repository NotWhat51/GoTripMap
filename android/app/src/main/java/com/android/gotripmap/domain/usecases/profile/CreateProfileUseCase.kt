package com.android.gotripmap.domain.usecases.profile

import com.android.gotripmap.domain.repositories.ProfileRepository

class CreateProfileUseCase(private val repository: ProfileRepository) {
  suspend operator fun invoke(id: Int,username: String,phone: String) {
    repository.createProfile(id,username,phone)
  }
}
