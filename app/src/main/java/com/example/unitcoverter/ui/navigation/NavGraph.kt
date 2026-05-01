package com.example.unitcoverter.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unitcoverter.ui.screens.CategoriesScreen
import com.example.unitcoverter.ui.screens.ConvertScreen
import com.example.unitcoverter.ui.screens.HistoryScreen
import com.example.unitcoverter.ui.screens.SettingsScreen
import com.example.unitcoverter.ui.viewmodel.ConvertViewModel
import com.example.unitcoverter.ui.viewmodel.SettingsViewModel

sealed class Screen(val route: String) {
    object Convert : Screen("convert")
    object Categories : Screen("categories")
    object History : Screen("history")
    object Settings : Screen("settings")
}

@Composable
fun NavGraph(
    navController: NavHostController,
    convertViewModel: ConvertViewModel = viewModel(),
    settingsViewModel: SettingsViewModel = viewModel()
) {
    // Sync settings with converter
    LaunchedEffect(settingsViewModel.decimalPlaces) {
        convertViewModel.setDecimalPlaces(settingsViewModel.decimalPlaces)
    }
    
    LaunchedEffect(settingsViewModel.unitSystem) {
        convertViewModel.setUnitSystem(settingsViewModel.unitSystem)
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Convert.route
    ) {
        composable(Screen.Convert.route) {
            ConvertScreen(viewModel = convertViewModel)
        }
        composable(Screen.Categories.route) {
            CategoriesScreen(
                onCategoryClick = { category ->
                    convertViewModel.onCategorySelected(category)
                    navController.navigate(Screen.Convert.route) {
                        popUpTo(Screen.Convert.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.History.route) {
            HistoryScreen(viewModel = convertViewModel)
        }
        composable(Screen.Settings.route) {
            SettingsScreen(viewModel = settingsViewModel)
        }
    }
}
