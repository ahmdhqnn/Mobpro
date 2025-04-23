package org.ahmad0122.mobpro1.navigation

sealed class MahasiswaScreen(val route: String) {
    data object Home: MahasiswaScreen("mahasiswaScreen")
    data object FormTambah: MahasiswaScreen("mahasiswaDetailScreen")
    data object FormUbah: MahasiswaScreen("mahasiswaDetailScreen/{$KEY_ID_MAHASISWA}") {
        fun withId(id: Long) = "mahasiswaDetailScreen/$id"
    }
}

const val KEY_ID_MAHASISWA = "idMahasiswa" 