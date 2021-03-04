package com.example.covidcasetracker.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.covidcasetracker.viewmodel.CovidViewModel

// Nav graph of this app
@Composable
fun AppNavigation(_viewmodel:CovidViewModel) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            Home( navController, _viewmodel)
        }
        composable(
            "taskDetail/{country}",
            arguments = listOf(
                navArgument("country") { type = NavType.StringType }
            )
        ) {
            DetailScreen(   data = it.arguments?.getString("country") ?: "Nullstring", navController)
        }
    }

}