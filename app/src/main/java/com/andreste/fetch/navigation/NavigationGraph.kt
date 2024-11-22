package com.andreste.fetch.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.andreste.fetch.screens.HomeScreen
import com.andreste.fetch.viewmodels.HiringViewModel

@Composable
fun NavigationGraph() {
    val navController = rememberNavController()
    val viewModel: HiringViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = Screen.Home.name) {
        composable(Screen.Home) {
            HomeScreen(navController, viewModel)
        }
    }
}