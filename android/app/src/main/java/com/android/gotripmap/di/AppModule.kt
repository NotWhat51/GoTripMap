package com.android.gotripmap.di


import com.android.gotripmap.data.db.RoutesAndEntriesDB
import com.android.gotripmap.data.mappers.RouteMapper
import com.android.gotripmap.data.mappers.RoutesAndEntriesMapper
import com.android.gotripmap.data.mappers.SearchEntryMapper
import com.android.gotripmap.data.repositories.EntriesRepositoryImpl
import com.android.gotripmap.data.repositories.RoutesRepositoryImpl
import com.android.gotripmap.domain.repositories.EntriesRepository
import com.android.gotripmap.domain.repositories.RoutesRepository
import com.android.gotripmap.domain.usecases.AddRouteUseCase
import com.android.gotripmap.domain.usecases.ChangeLikedUseCase
import com.android.gotripmap.domain.usecases.GetCurrentRoutesUseCase
import com.android.gotripmap.domain.usecases.GetHistoryUseCase
import com.android.gotripmap.domain.usecases.GetLikedUseCase
import com.android.gotripmap.domain.usecases.MakeEntryCurrentUseCase
import com.android.gotripmap.presentation.viewmodels.HistoryVM
import com.android.gotripmap.presentation.viewmodels.LikedVM
import com.android.gotripmap.presentation.viewmodels.SearchScreenVM
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.compose.get
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Инъекция зависимостей
 */
val appModule = module {
  single {
    RoutesAndEntriesDB.create(androidContext()).mainDao()
  }
  single {
    SearchEntryMapper()
  }
  single {
    RouteMapper()
  }
  single {
    RoutesAndEntriesMapper(get(),get())
  }

  single<EntriesRepository>{
    EntriesRepositoryImpl(get(),get(),get())
  }
  single<RoutesRepository> {
    RoutesRepositoryImpl(get(),get())
  }
  single {
    GetHistoryUseCase(get())
  }
  single{
    GetLikedUseCase(get())
  }
  single{
    ChangeLikedUseCase(get())
  }
  single{
    AddRouteUseCase(get())
  }
  single {
    MakeEntryCurrentUseCase(get())
  }

  single {
    GetCurrentRoutesUseCase(get())
  }

  viewModel { HistoryVM(get(),get())  }
  viewModel { LikedVM(get(),get()) }
  viewModel { SearchScreenVM(get(),get()) }
}
