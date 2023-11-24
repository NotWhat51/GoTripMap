package com.android.gotripmap.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.gotripmap.domain.entities.SearchEntry
import com.android.gotripmap.domain.entities.Transport
import com.android.gotripmap.domain.usecases.ChangeLikedUseCase
import com.android.gotripmap.domain.usecases.GetCurrentRoutesUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SearchScreenVM(val changeLikedUseCase: ChangeLikedUseCase,getCurrentRoutesUseCase: GetCurrentRoutesUseCase): ViewModel() {
  val currentEntry = getCurrentRoutesUseCase().map { it?.searchEntry }.stateIn(
    viewModelScope,
    SharingStarted.WhileSubscribed(5000),
    SearchEntry(0,"Error","wrong",Transport.WALKING)
  )
  val routes = getCurrentRoutesUseCase().map { it?.routes }.stateIn(
    viewModelScope,
    SharingStarted.WhileSubscribed(5000),
    listOf()
  )

  fun changeLiked(id: Int) {
    viewModelScope.launch {
      changeLikedUseCase(id)
    }
  }
}
