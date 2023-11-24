package com.android.gotripmap.domain.usecases

import com.android.gotripmap.domain.repositories.EntriesRepository

class MakeEntryCurrentUseCase(private val entriesRepository: EntriesRepository) {
  suspend operator fun invoke(id: Int) {
    entriesRepository.makeEntryCurrent(id)
  }
}
