package org.ahmad0122.mobpro1.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.ahmad0122.mobpro1.database.TransaksiDao
import org.ahmad0122.mobpro1.model.Transaksi

class DetailViewModel(private val dao: TransaksiDao) : ViewModel() {
    fun insert(jenis: String, kategori: String, jumlah: Double, deskripsi: String, tanggal: String) {
        val transaksi = Transaksi(
            jenis = jenis,
            kategori = kategori,
            jumlah = jumlah,
            deskripsi = deskripsi,
            tanggal = tanggal
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(transaksi)
        }
    }

    suspend fun getTransaksi(id: Long): Transaksi? {
        return dao.getTransaksiById(id)
    }

    fun update(id: Long, jenis: String, kategori: String, jumlah: Double, deskripsi: String, tanggal: String) {
        val transaksi = Transaksi(
            id = id,
            jenis = jenis,
            kategori = kategori,
            jumlah = jumlah,
            deskripsi = deskripsi,
            tanggal = tanggal
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.update(transaksi)
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.softDeleteById(id)
        }
    }
}