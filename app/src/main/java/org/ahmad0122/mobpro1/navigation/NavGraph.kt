package org.ahmad0122.mobpro1.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.ahmad0122.mobpro1.ui.screen.DetailScreen
import org.ahmad0122.mobpro1.ui.screen.KEY_ID_CATATAN
import org.ahmad0122.mobpro1.ui.screen.MainScreen
import org.ahmad0122.mobpro1.ui.screen.MahasiswaScreen
import org.ahmad0122.mobpro1.ui.screen.MahasiswaDetailScreen

@Composable
fun SetUpNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = MahasiswaScreen.Home.route
    ) {
        // Navigasi Catatan
        composable(route = Screen.Home.route) {
            MainScreen(navController)
        }
        composable(route = Screen.FormBaru.route) {
            DetailScreen(navController)
        }
        composable(
            route = Screen.FormUbah.route,
            arguments = listOf(
                navArgument(KEY_ID_CATATAN) { type = NavType.LongType  }
            )
        ) { navBackStackEntry ->
            val id  = navBackStackEntry.arguments?.getLong(KEY_ID_CATATAN)
            DetailScreen(navController, id)
        }
        
        // Navigasi Mahasiswa
        composable(route = MahasiswaScreen.Home.route) {
            MahasiswaScreen(navController)
        }
        composable(route = MahasiswaScreen.FormTambah.route) {
            MahasiswaDetailScreen(navController)
        }
        composable(
            route = MahasiswaScreen.FormUbah.route,
            arguments = listOf(
                navArgument(KEY_ID_MAHASISWA) { type = NavType.LongType }
            )
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getLong(KEY_ID_MAHASISWA)
            MahasiswaDetailScreen(navController, id)
        }
    }
}