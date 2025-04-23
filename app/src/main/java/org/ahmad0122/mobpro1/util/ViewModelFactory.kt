package org.ahmad0122.mobpro1.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.ahmad0122.mobpro1.MainViewModel
import org.ahmad0122.mobpro1.database.CatatanDb
import org.ahmad0122.mobpro1.ui.screen.DetailViewModel


class ViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dao = CatatanDb.getInstance(context).dao

        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(dao) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
                return DetailViewModel(dao) as T
            }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}