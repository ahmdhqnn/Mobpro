package org.ahmad0122.mobpro1.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "catatan")
data class Catatan(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val tanggal: String,
    val judul: String,
    val catatan: String
)