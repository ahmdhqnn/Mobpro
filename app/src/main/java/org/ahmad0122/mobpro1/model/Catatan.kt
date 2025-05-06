package org.ahmad0122.mobpro1.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaksi")
data class Transaksi(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val tanggal: String,
    val jenis: String, // "PENDAPATAN" atau "PENGELUARAN"
    val kategori: String,
    val jumlah: Double,
    val deskripsi: String,
    val isDeleted: Boolean = false // Untuk fitur recycle bin
)