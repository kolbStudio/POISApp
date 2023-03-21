package com.testpois.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.testpois.features.getPois.ui.PoiDetailScreen
import com.testpois.features.getPois.ui.PoiScreen
import com.testpois.features.getPois.ui.PoiViewModel
import com.testpois.ui.model.Routes

@Composable
fun AppNavigation(viewModel: PoiViewModel) {
    val navController = rememberNavController()
    val uiState = viewModel.uiState.collectAsState().value

    NavHost(
        navController = navController,
        startDestination = Routes.PoiScreen.route,
    ) {
        composable(Routes.PoiScreen.route) { PoiScreen(uiState, navController, viewModel) }
        composable(
            route = Routes.PoiDetailScreen.route,
            arguments = listOf(
                navArgument("title") { type = NavType.StringType },
                navArgument("geocoordinates") { type = NavType.StringType },
                navArgument("image") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            val title = backStackEntry.arguments?.getString("title")
            val geocoordinates = backStackEntry.arguments?.getString("geocoordinates")
            val image = backStackEntry.arguments?.getString("image")

            PoiDetailScreen(navController, title, geocoordinates, image, viewModel)

        }
    }
}
