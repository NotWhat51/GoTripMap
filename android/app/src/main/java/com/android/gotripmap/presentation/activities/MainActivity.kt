package com.android.gotripmap.presentation.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.android.gotripmap.R
import com.android.gotripmap.data.mapkit.SearchMap
import com.android.gotripmap.presentation.screens.MainScreen
import com.android.gotripmap.ui.theme.GoTripMapTheme
import com.android.gotripmap.ui.theme.GradientStart
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Geometry
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.search.Response
import com.yandex.mapkit.search.SearchFactory
import com.yandex.mapkit.search.SearchManagerType
import com.yandex.mapkit.search.SearchOptions
import com.yandex.mapkit.search.Session
import com.yandex.runtime.Error

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    MapKitFactory.initialize(this)
    setContent {
      GoTripMapTheme {
        MainScreen()
      }
    }
  }

  override fun onStart() {
    MapKitFactory.getInstance().onStart()
    super.onStart()
  }

  override fun onStop() {
    MapKitFactory.getInstance().onStop()
    super.onStop()
  }
}
