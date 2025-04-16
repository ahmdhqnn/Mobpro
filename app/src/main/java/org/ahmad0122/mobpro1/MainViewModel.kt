package org.ahmad0122.mobpro1

import androidx.lifecycle.ViewModel
import org.ahmad0122.mobpro1.model.Mahasiswa

class MainViewModel: ViewModel() {
    val data = listOf(
        Mahasiswa(
            1,
            "Rizza Indah Mega Mandasari",
            "6706244601",
            "D3IF-46-01"
        ),
        Mahasiswa(
            2,
            "Indra Azimi",
            "6706244602",
            "D3IF-46-02"
        ),
        Mahasiswa(
            3,
            "Reza Budiawan",
            "6706244612",
            "D3IF-46-02"
        ),
        Mahasiswa(
            4,
            "Dwiko Indrawansyah",
            "6706244622",
            "D3IF-46-02"
        ),
        Mahasiswa(
            5,
            "Cahyana",
            "6706244603",
            "D3IF-46-03"
        ),
        Mahasiswa(
            6,
            "Indra Azimi",
            "6706244604",
            "D3IF-46-04"
        ),
        Mahasiswa(
            7,
            "Erna Hikmawati",
            "6706244605",
            "D3IF-46-05"
        )
    )

    fun getMahasiswa(id: Long): Mahasiswa? {
        return data.find { it.id == id }
    }
}