package org.ahmad0122.mobpro1.navigation

import org.ahmad0122.mobpro1.ui.screen.KEY_ID_TRANSAKSI

sealed class Screen(val route: String) {
    data object Home: Screen("mainScreen")
    data object FormBaru: Screen("detailScreen")
    data object FormUbah: Screen("detailScreen/{$KEY_ID_TRANSAKSI}") {
        fun withId(id: Long) = "detailScreen/$id"
    }
}