package com.android.gotripmap.data.mappers

import com.android.gotripmap.data.db.RouteDbModel
import com.android.gotripmap.data.pojo.RouteResponse
import com.android.gotripmap.domain.entities.Coordinate
import com.android.gotripmap.domain.entities.Route
import com.android.gotripmap.domain.entities.SearchEntry
import com.android.gotripmap.domain.entities.Transport

/**
 * Маппер для маршрута
 */
class RouteMapper {
  private fun mapDbToDtModel(routeDbModel: RouteDbModel) =
    Route(id=routeDbModel.id,
      endPoint = Coordinate(routeDbModel.endPointX,routeDbModel.endPointY),
      length = routeDbModel.length,
      startPoint = Coordinate(routeDbModel.startPointX,routeDbModel.startPointY),
      endPointPlace = routeDbModel.endPointPlace,
      endPointAddress = routeDbModel.endPointAddress,
      imageLink = routeDbModel.imageLink,
      timeRequired = routeDbModel.timeRequired,
      startPointAddress = routeDbModel.startPointAddress,
      startPointPlace = routeDbModel.startPointPlace,
      transport = routeDbModel.transport,
      searchEntry= routeDbModel.searchEntry,
      liked = routeDbModel.liked)


  fun mapApiToDbModel(response: RouteResponse,searchEntry:SearchEntry) =
    RouteDbModel(
      length = response.length,
      startPointX = response.startPointX,
      startPointY = response.startPointY,
      startPointPlace = response.startPointPlace,
      startPointAddress = response.startPointAddress,
      endPointX = response.endPointX,
      endPointY = response.endPointY,
      endPointPlace = response.endPointPlace,
      endPointAddress = response.endPointAddress,
      imageLink = response.imageLink,
      timeRequired = response.timeRequired,
      transport = searchEntry.transport,
      searchEntry = searchEntry.id
    )

  fun mapDbListToDtList(dbList: List<RouteDbModel>): List<Route> =
    dbList.map { mapDbToDtModel(it) }

  fun mapApiListToDbList(apiList: List<RouteResponse>,searchEntry:SearchEntry): List<RouteDbModel> =
    apiList.map { mapApiToDbModel(it, searchEntry) }

}
