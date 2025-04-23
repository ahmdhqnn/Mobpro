package org.ahmad0122.mobpro1.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "mahasiswa")
data class Mahasiswa(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val nim: String,
    val nama: String,
    val jurusan: String
)