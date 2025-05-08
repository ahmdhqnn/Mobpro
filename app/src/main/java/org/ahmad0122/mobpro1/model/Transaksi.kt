package org.ahmad0122.mobpro1.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "transaksi")
data class Transaksi(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val keterangan: String,
    val jumlah: Double,
    val tanggal: Date,
    val jenis: String,
    val isDeleted: Boolean = false
) 