package org.ahmad0122.mobpro1.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Add : Screen("add")
    object Edit : Screen("edit/{transaksiId}") {
        fun createRoute(transaksiId: Long) = "edit/$transaksiId"
        
        val arguments = listOf(
            navArgument("transaksiId") {
                type = NavType.LongType
            }
        )
    }
    object RecycleBin : Screen("recycle_bin")
}