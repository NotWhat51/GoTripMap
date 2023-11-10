package com.android.gotripmap.presentation.bottom_navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.android.gotripmap.presentation.screens.MenuScreen
import com.android.gotripmap.presentation.screens.ProfileScreen
import com.android.gotripmap.presentation.screens.SearchScreen

@Composable
fun NavGraph(
  navHostController: NavHostController
) {
  NavHost(navController = navHostController, startDestination= BottomItem.SEARCH_SCREEN){
    composable(BottomItem.SEARCH_SCREEN) {
      SearchScreen()
    }
    composable(BottomItem.MENU_SCREEN) {
      MenuScreen()
    }
    composable(BottomItem.PROFILE_SCREEN) {
      ProfileScreen()
    }
  }
}
