package com.android.gotripmap.domain.repositories

import com.android.gotripmap.domain.entities.Route
import com.android.gotripmap.domain.entities.SearchEntry
import kotlinx.coroutines.flow.Flow

interface RoutesRepository {
  val likedRoutes: Flow<List<Route>>
  suspend fun insertRoute(route: Route)
  suspend fun changeLiked(id: Int)
  suspend fun deleteRecentRoutes()


}
