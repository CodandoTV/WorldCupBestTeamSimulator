package com.codandotv.worldcupbestteamsimulator

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.codandotv.worldcupbestteamsimulator.ui.home.HomeScreen
import com.codandotv.worldcupbestteamsimulator.ui.search.SearchPlayersScreen

val LocalNavController = compositionLocalOf<NavHostController> { error("No NavController found!") }

const val SEARCH_ROUTE = "search"
const val HOME_ROUTE = "home"

@Composable
@Preview
fun App() {
    val navigator = rememberNavController()
    MaterialTheme {
        CompositionLocalProvider(LocalNavController provides navigator) {
            NavHost(
                navController = navigator,
                startDestination = HOME_ROUTE
            ) {
                composable(
                    route = HOME_ROUTE
                ) {
                    HomeScreen()
                }

                composable(route = SEARCH_ROUTE) {
                    SearchPlayersScreen()
                }
            }
        }
    }
}
