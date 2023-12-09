package com.android.gotripmap.data.repositories

import com.android.gotripmap.data.db.MainDAO
import com.android.gotripmap.data.mappers.RouteMapper
import com.android.gotripmap.data.network.TestApiService
import com.android.gotripmap.data.pojo.RouteResponse
import com.android.gotripmap.data.pojo.SearchRequest
import com.android.gotripmap.domain.entities.MyAddress
import com.android.gotripmap.domain.entities.Route
import com.android.gotripmap.domain.entities.SearchEntry
import com.android.gotripmap.domain.repositories.RoutesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoutesRepositoryImpl(private val mainDAO: MainDAO,
                           private val routeMapper: RouteMapper,
                           private val testApiService: TestApiService): RoutesRepository {


  override val likedRoutes: Flow<List<Route>>
    get() = mainDAO.getLikedRoutes().map { routeMapper.mapDbListToDtList(it) }

  override suspend fun loadRoutesForEntry(entry: SearchEntry, coordinates: MyAddress): List<RouteResponse> {
    val routes = testApiService.getRoutesForEntry(SearchRequest(entry.entry,entry.transport,coordinates))
    routeMapper.mapApiListToDbList(routes,entry).forEach {
      mainDAO.insertRoute(it)
    }
    return routes
  }

  override suspend fun changeLiked(id: Int) {
    mainDAO.changeLiked(id)
  }

  override suspend fun deleteRecentRoutes(id: Int) {
    mainDAO.deleteRecentRoutes(id)
  }
}
