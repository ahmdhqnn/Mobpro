package org.ahmad0122.mobpro1.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.ahmad0122.mobpro1.MainViewModel
import org.ahmad0122.mobpro1.MahasiswaViewModel
import org.ahmad0122.mobpro1.database.CatatanDb
import org.ahmad0122.mobpro1.database.MahasiswaDb
import org.ahmad0122.mobpro1.ui.screen.DetailViewModel
import org.ahmad0122.mobpro1.ui.screen.MahasiswaDetailViewModel

class ViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val catatanDao = CatatanDb.getInstance(context).dao
        val mahasiswaDao = MahasiswaDb.getInstance(context).dao

        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(catatanDao) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(catatanDao) as T
            }
            modelClass.isAssignableFrom(MahasiswaViewModel::class.java) -> {
                MahasiswaViewModel(mahasiswaDao) as T
            }
            modelClass.isAssignableFrom(MahasiswaDetailViewModel::class.java) -> {
                MahasiswaDetailViewModel(mahasiswaDao) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}