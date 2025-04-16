package org.ahmad0122.mobpro1

import androidx.lifecycle.ViewModel
import org.ahmad0122.mobpro1.model.Catatan

class MainViewModel: ViewModel() {
    val data = listOf(
        Catatan(
            1,
            "Kuliah Mobpro 17 Feb",
            "Kuliah hari pertama. Ternyata keren juga yang ma...",
            "2025-02-17 12:34:56"
        ),
        Catatan(
            2,
            "Kuliah Mobpro 19 Feb",
            "Praktikum pertama: running modul. Alhamdulillah...",
            "2025-02-19 12:34:56"
        ),
        Catatan(
            3,
            "Ini Data Dump 1",
            "Dump dump dump 1",
            "2025-02-19 12:34:56"
        ),Catatan(
            4,
            "Ini Data Dump 2",
            "Dump dump dump 2",
            "2025-02-19 12:34:56"
        ),
        Catatan(
            5,
            "Kuliah Mobpro 05 Mar",
            "Praktikum kali ini bikin aplikasi Galeri Hewan.",
            "2025-03-05 12:34:56"
        )
    )

    fun getCatatan(id: Long): Catatan? {
        return data.find { it.id == id }
    }
}