package org.ahmad0122.mobpro1.navigation

sealed class Screen(val route: String) {
    data object Home: Screen("mainScreen")
}