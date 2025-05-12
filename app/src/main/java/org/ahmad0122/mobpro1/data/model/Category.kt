package org.ahmad0122.mobpro1.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val color: Int,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
) 