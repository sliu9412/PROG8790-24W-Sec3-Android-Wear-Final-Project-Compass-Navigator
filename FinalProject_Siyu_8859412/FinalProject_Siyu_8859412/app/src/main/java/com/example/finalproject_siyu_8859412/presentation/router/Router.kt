package com.example.finalproject_siyu_8859412.presentation.router

import androidx.compose.runtime.Composable
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.example.finalproject_siyu_8859412.presentation.LocationHelper
import com.example.finalproject_siyu_8859412.presentation.StoreHelper
import com.example.finalproject_siyu_8859412.presentation.screens.CompassScreen
import com.example.finalproject_siyu_8859412.presentation.screens.DefaultScreen
import com.example.finalproject_siyu_8859412.presentation.screens.ListScreen

enum class Routes {
    DEFAULT_SCREEN,
    LIST_SCREEN,
    COMPASS_SCREEN
}

@Composable
fun Router(locationHelper: LocationHelper, storeHelper: StoreHelper) {
    val navController = rememberSwipeDismissableNavController()
    SwipeDismissableNavHost(
        navController = navController,
        startDestination = Routes.DEFAULT_SCREEN.toString()
    ) {
        composable(Routes.DEFAULT_SCREEN.toString()) {
            DefaultScreen(navController, locationHelper, storeHelper)
        }
        composable(Routes.LIST_SCREEN.toString()) {
            ListScreen(navController, locationHelper, storeHelper)
        }
        composable(Routes.COMPASS_SCREEN.toString()) {
            CompassScreen(locationHelper)
        }
    }
}