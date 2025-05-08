package org.ahmad0122.mobpro1.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.ahmad0122.mobpro1.ui.screens.AddScreen
import org.ahmad0122.mobpro1.ui.screens.EditScreen
import org.ahmad0122.mobpro1.ui.screens.HomeScreen
import org.ahmad0122.mobpro1.ui.screens.RecycleBinScreen
import org.ahmad0122.mobpro1.ui.viewmodel.MainViewModel

@Composable
fun Navigation(navController: NavHostController) {
    val viewModel: MainViewModel = viewModel()
    
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToAdd = {
                    navController.navigate(Screen.Add.route)
                },
                onNavigateToEdit = { transaksiId ->
                    viewModel.setCurrentTransaksiId(transaksiId)
                    navController.navigate(Screen.Edit.createRoute(transaksiId))
                },
                onNavigateToRecycleBin = {
                    navController.navigate(Screen.RecycleBin.route)
                }
            )
        }
        
        composable(Screen.Add.route) {
            AddScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(
            route = Screen.Edit.route,
            arguments = Screen.Edit.arguments
        ) {
            EditScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(Screen.RecycleBin.route) {
            RecycleBinScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
} 