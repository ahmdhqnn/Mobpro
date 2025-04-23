package org.ahmad0122.mobpro1.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mahasiswa")
data class Mahasiswa(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val nama: String,
    val nim: String,
    val kelas: String
) 