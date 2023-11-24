package com.android.gotripmap.data.repositories

import com.android.gotripmap.data.db.MainDAO
import com.android.gotripmap.data.db.RouteDbModel
import com.android.gotripmap.data.mappers.RouteMapper
import com.android.gotripmap.domain.entities.Route
import com.android.gotripmap.domain.entities.Transport
import com.android.gotripmap.domain.repositories.RoutesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class RoutesRepositoryImpl(private val mainDAO: MainDAO,
                           private val routeMapper: RouteMapper
): RoutesRepository {

  init {
    val scope = CoroutineScope(Dispatchers.Default)
    scope.launch {
      mainDAO.insertRoute(RouteDbModel(length="12m", startPointX = 59.935481F, startPointY = 30.327107F, startPointPlace = "blablabla", startPointAddress = "bla1", endPointX = 59.934067F, endPointY = 30.332764F, endPointPlace = "blablabla", endPointAddress = "bla3", imageLink = "https://images.squarespace-cdn.com/content/v1/5e97431c825c59018588244f/1654001522957-YFTVZKCUY947UN5KCF15/IMG_8987.jpg", timeRequired = "15 m", transport = Transport.PUBLIC, liked = false, searchEntry = 1))
      mainDAO.insertRoute(RouteDbModel(length="15m", startPointX = 59.935481F, startPointY = 30.327107F, startPointPlace = "blablabla", startPointAddress = "bla2", endPointX = 59.934067F, endPointY = 30.332764F, endPointPlace = "blablabla", endPointAddress = "bla4", imageLink = "https://images.squarespace-cdn.com/content/v1/5e97431c825c59018588244f/1654001522957-YFTVZKCUY947UN5KCF15/IMG_8987.jpg", timeRequired = "15 m", transport = Transport.PUBLIC, liked = true, searchEntry = 2))

    }
  }
  override val likedRoutes: Flow<List<Route>>
    get() = mainDAO.getLikedRoutes().map { routeMapper.mapDbListToDtList(it) }

  override suspend fun insertRoute(route: Route) {
    mainDAO.insertRoute(routeMapper.mapDtToDbModel(route))
  }

  override suspend fun changeLiked(id: Int) {
    mainDAO.changeLiked(id)
  }

  override suspend fun deleteRecentRoutes() {
    mainDAO.deleteRecentRoutes()
  }
}
