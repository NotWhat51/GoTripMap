package com.android.gotripmap.data.mappers

import com.android.gotripmap.data.db.RouteDbModel
import com.android.gotripmap.domain.entities.Coordinate
import com.android.gotripmap.domain.entities.Route

/**
 * Маппер для маршрута
 */
class RouteMapper {
  private fun mapDbToDtModel(routeDbModel: RouteDbModel) =
    Route(id=routeDbModel.id,
      endPoint = Coordinate(routeDbModel.endPointX,routeDbModel.endPointY),
      length = routeDbModel.length,
      startPoint = Coordinate(routeDbModel.startPointX,routeDbModel.startPointY),
      endPointPlace = routeDbModel.endPointAddress,
      endPointAddress = routeDbModel.endPointPlace,
      imageLink = routeDbModel.imageLink,
      timeRequired = routeDbModel.timeRequired,
      startPointAddress = routeDbModel.startPointAddress,
      startPointPlace = routeDbModel.startPointPlace,
      transport = routeDbModel.transport,
      searchEntry= routeDbModel.searchEntry,
      liked = routeDbModel.liked)

  fun mapDtToDbModel(route: Route) =
    RouteDbModel(id=route.id,
      endPointX = route.endPoint.x,
      endPointY = route.endPoint.y,
      length = route.length,
      startPointX = route.startPoint.x,
      startPointY = route.startPoint.y,
      endPointAddress = route.endPointPlace,
      endPointPlace = route.endPointAddress,
      imageLink = route.imageLink,
      timeRequired = route.timeRequired,
      startPointAddress = route.startPointAddress,
      startPointPlace = route.startPointPlace,
      transport = route.transport,
      searchEntry = route.searchEntry,
      liked = route.liked)

  fun mapDbListToDtList(dbList: List<RouteDbModel>): List<Route> =
    dbList.map { mapDbToDtModel(it) }

}
