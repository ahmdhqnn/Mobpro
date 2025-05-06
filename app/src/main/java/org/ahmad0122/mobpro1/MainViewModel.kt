package org.ahmad0122.mobpro1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.ahmad0122.mobpro1.database.TransaksiDao
import org.ahmad0122.mobpro1.model.Transaksi

class MainViewModel(private val dao: TransaksiDao) : ViewModel() {
    val transaksi: StateFlow<List<Transaksi>> = dao.getTransaksi().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

    val transaksiTerhapus: StateFlow<List<Transaksi>> = dao.getTransaksiTerhapus().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

    val totalPendapatan: StateFlow<Double> = dao.getTotalPendapatan().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = 0.0
    )

    val totalPengeluaran: StateFlow<Double> = dao.getTotalPengeluaran().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = 0.0
    )

    fun tambahTransaksi(transaksi: Transaksi) {
        viewModelScope.launch {
            dao.insert(transaksi)
        }
    }

    fun updateTransaksi(transaksi: Transaksi) {
        viewModelScope.launch {
            dao.update(transaksi)
        }
    }

    fun softDeleteTransaksi(id: Long) {
        viewModelScope.launch {
            dao.softDeleteById(id)
        }
    }

    fun restoreTransaksi(id: Long) {
        viewModelScope.launch {
            dao.restoreById(id)
        }
    }

    fun deletePermanenTransaksi(id: Long) {
        viewModelScope.launch {
            dao.deletePermanentlyById(id)
        }
    }
}