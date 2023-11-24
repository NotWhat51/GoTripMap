package com.android.gotripmap.presentation.maps

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import com.android.gotripmap.domain.entities.Coordinate
import com.android.gotripmap.domain.entities.Transport

/**
 * Класс для открытия маршрута в картах гугла и яндекса
 */
class OpenMaps(private val context: Context) {

  /**
   * Проверка, установлено ли приложение карт
   */
  private fun isPackageInstalled(packageName: String, packageManager: PackageManager): Boolean {
    return try {
      packageManager.getPackageInfo(packageName, 0)
      true
    } catch (e: PackageManager.NameNotFoundException) {
      false
    }
  }
  /**
   * Функция для получения ссылки для интента в зависимости от карты
   */
  private fun getMapLink(
    startPoint: Coordinate,
    endPoint: Coordinate,
    transport: Transport,
    mapType: Maps
  ): Uri {
    val link = when (mapType) {
      is Maps.YandexMaps -> "yandexmaps://maps.yandex.ru/?rtext=${startPoint.x}%2C${startPoint.y}~${endPoint.x}%2C${endPoint.y}&rtt=" + when (transport) {
        Transport.WALKING -> "pd"
        Transport.BICYCLE -> "bc"
        Transport.CAR -> "auto"
        Transport.PUBLIC -> "mt"
      }

      Maps.GoogleMaps -> "https://www.google.com/maps/dir/?api=1&origin=${startPoint.x},${startPoint.y}&destination=${endPoint.x},${endPoint.y}&travelmode=" + when (transport) {
        Transport.WALKING -> "walking"
        Transport.BICYCLE -> "bicycling"
        Transport.CAR -> "driving"
        Transport.PUBLIC -> "transit"
      }
    }
    return Uri.parse(link)
  }

  /**
   * Функция для создания диалога с пользователем при открытии маршрута
   */
  fun openMaps(
    startPoint: Coordinate,
    endPoint: Coordinate,
    transport: Transport
  ) {
    val pm = context.packageManager
    val intents = mutableListOf<Intent>()
    val listMaps = listOf(Maps.GoogleMaps, Maps.YandexMaps)
    for (app in listMaps) {
      if (!isPackageInstalled(app.pack, pm)) {
        continue
      }
      val targetIntent =
        Intent(Intent.ACTION_VIEW, getMapLink(startPoint, endPoint, transport, app))
      targetIntent.setPackage(app.pack)
      intents.add(targetIntent)
    }
    if (intents.isEmpty()) {
      //Добавить обработку возможной ошибки
      return
    }
    val chooserIntent =
      Intent.createChooser(intents.removeAt(0), "Select app to share")
    chooserIntent.putExtra(
      Intent.EXTRA_INITIAL_INTENTS, intents.toTypedArray()
    )
    context.startActivity(chooserIntent)
  }
}
