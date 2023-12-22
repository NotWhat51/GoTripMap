package com.android.gotripmap

import android.app.Application
import com.android.gotripmap.di.appModule
import com.yandex.mapkit.MapKitFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GoTripMap: Application() {
  override fun onCreate() {
    super.onCreate()
    MapKitFactory.setApiKey("1c7699cf-6a79-4ac0-8155-5c4e8311e11b")
    startKoin {
      androidContext(this@GoTripMap)
      modules(appModule)
    }
  }
}
