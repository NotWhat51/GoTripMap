package com.android.gotripmap.data.repositories

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Looper
import android.util.Log
import com.android.gotripmap.R
import com.android.gotripmap.checkLocationPermission
import com.android.gotripmap.domain.entities.Coordinate
import com.android.gotripmap.domain.entities.MyAddress
import com.android.gotripmap.domain.repositories.PiLocationManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class PiLocationManagerImpl (val context: Context, val geocoder: Geocoder): PiLocationManager {

  private val fusedLocationClient: FusedLocationProviderClient by lazy {
    LocationServices.getFusedLocationProviderClient(context)
  }


  override fun locationUpdates(intervalInMillis: Long): Flow<MyAddress> {
    return callbackFlow {
      if (!context.checkLocationPermission()) {
        Log.w("address_start","fail")
        launch {
          send(MyAddress(Coordinate(),context.getString(R.string.missing_location_permission)))
        }
      }
      if (!context.isNetworkOrGPSEnabled()) {
        Log.w("address_start","fail")
        launch {
          send(MyAddress(Coordinate(),context.getString(R.string.network_or_gps_is_not_available)))
        }
      }

      val request = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, intervalInMillis).setWaitForAccurateLocation(true).build()

      val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
          super.onLocationResult(result)
          result.locations.lastOrNull()?.let {location: Location ->
            launch {
              Log.w("address",location.longitude.toString())
              val address = geocoder.getAddress(location.latitude,location.longitude)?.getAddressLine(0)
              send(MyAddress(Coordinate(location.latitude,location.longitude),address))
            }
          }
        }
      }
      fusedLocationClient.requestLocationUpdates(request, locationCallback, Looper.getMainLooper())
      awaitClose {
        Log.w("address_close","close")
        fusedLocationClient.removeLocationUpdates(locationCallback)
      }

    }

  }

  private suspend fun Geocoder.getAddress(
    latitude: Double,
    longitude: Double,
  ): Address? = withContext(Dispatchers.IO) {
    try {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        suspendCoroutine { cont ->
          getFromLocation(latitude, longitude, 1) {
            cont.resume(it.firstOrNull())
          }
        }
      } else {
        suspendCoroutine { cont ->
          @Suppress("DEPRECATION")
          val address = getFromLocation(latitude, longitude, 1)?.firstOrNull()
          cont.resume(address)
        }
      }
    } catch (e: Exception) {
      null
    }
  }

  private fun Context.isNetworkOrGPSEnabled():Boolean {
    val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    return isGpsEnabled || isNetworkEnabled
  }

}
