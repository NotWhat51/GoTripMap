package com.android.gotripmap.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.android.gotripmap.presentation.bottom_navigation.BottomNavigation
import com.android.gotripmap.presentation.bottom_navigation.NavGraph

//Как должен выглядеть главный экран при первом запуске?
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen() {
  val navController = rememberNavController()

  Scaffold(bottomBar = {
    BottomNavigation(navController = navController)
  }) {
    NavGraph(navHostController = navController)
  }
}
