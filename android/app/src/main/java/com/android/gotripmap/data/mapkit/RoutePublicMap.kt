package com.android.gotripmap.data.mapkit

import android.util.Log
import com.android.gotripmap.domain.entities.RouteIntermediateResults
import com.yandex.mapkit.RequestPoint
import com.yandex.mapkit.RequestPointType
import com.yandex.mapkit.transport.TransportFactory
import com.yandex.mapkit.transport.masstransit.Route
import com.yandex.mapkit.transport.masstransit.Session
import com.yandex.mapkit.transport.masstransit.TransitOptions
import com.yandex.runtime.Error

class RoutePublicMap(
  private val intermediateResults: RouteIntermediateResults,
  private val callback: (
    length: String, time: String, intermediateResults: RouteIntermediateResults
  ) -> Unit
): Session.RouteListener {

  init {
    val masstransitRouter = TransportFactory.getInstance().createMasstransitRouter()
    val transitOptions = TransitOptions()
    val requestPoints: List<RequestPoint> =
      intermediateResults.points.map { RequestPoint(it, RequestPointType.WAYPOINT, null, null) }
    masstransitRouter.requestRoutes(requestPoints, transitOptions, this)
  }
  override fun onMasstransitRoutes(routes: MutableList<Route>) {
    val route = routes.first()
    callback(route.metadata.weight.walkingDistance.text,route.metadata.weight.time.text, intermediateResults)
  }

  override fun onMasstransitRoutesError(p0: Error) {
    Log.w("masstransit_error",p0.toString())
  }
}
