package org.ahmad0122.mobpro1.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.ahmad0122.mobpro1.data.UserPreferences
import org.ahmad0122.mobpro1.database.TransaksiDb
import org.ahmad0122.mobpro1.model.Transaksi

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val database = TransaksiDb.getInstance(application)
    private val userPreferences = UserPreferences(application)

    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    private val _isGridView = MutableStateFlow(false)
    val isGridView: StateFlow<Boolean> = _isGridView.asStateFlow()

    var currentTransaksiId: Long? = null
        private set

    init {
        viewModelScope.launch {
            userPreferences.darkModeFlow.collect { isDark ->
                _isDarkMode.value = isDark
            }
        }
        viewModelScope.launch {
            userPreferences.gridViewFlow.collect { isGrid ->
                _isGridView.value = isGrid
            }
        }
    }

    val transaksi = database.dao.getTransaksi()
    val transaksiTerhapus = database.dao.getTransaksiTerhapus()
    val totalPendapatan = database.dao.getTotalPendapatan()
    val totalPengeluaran = database.dao.getTotalPengeluaran()

    fun setCurrentTransaksiId(id: Long) {
        currentTransaksiId = id
    }

    suspend fun getTransaksiById(id: Long): Transaksi? {
        return database.dao.getTransaksiById(id)
    }

    fun insertTransaksi(transaksi: Transaksi) {
        viewModelScope.launch {
            database.dao.insert(transaksi)
        }
    }

    fun updateTransaksi(transaksi: Transaksi) {
        viewModelScope.launch {
            database.dao.update(transaksi)
        }
    }

    fun softDeleteTransaksi(id: Long) {
        viewModelScope.launch {
            database.dao.softDeleteById(id)
        }
    }

    fun restoreTransaksi(id: Long) {
        viewModelScope.launch {
            database.dao.restoreById(id)
        }
    }

    fun deletePermanently(id: Long) {
        viewModelScope.launch {
            database.dao.deletePermanentlyById(id)
        }
    }

    fun setDarkMode(enabled: Boolean) {
        viewModelScope.launch {
            userPreferences.setDarkMode(enabled)
        }
    }

    fun setGridView(enabled: Boolean) {
        viewModelScope.launch {
            userPreferences.setGridView(enabled)
        }
    }
}