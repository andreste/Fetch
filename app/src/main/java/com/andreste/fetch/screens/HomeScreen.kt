package com.andreste.fetch.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.andreste.fetch.viewmodels.HiringViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: HiringViewModel) {
    LaunchedEffect(Unit) {
        viewModel.start(navController)
        viewModel.getItems()
    }
    TextField(value = "hello", onValueChange = {})

}
