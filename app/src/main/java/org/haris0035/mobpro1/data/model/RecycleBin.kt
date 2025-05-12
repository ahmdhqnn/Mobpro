package org.haris0035.mobpro1.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "recycle_bin")
data class RecycleBin(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val taskId: Long,
    val title: String,
    val description: String,
    val dueDate: Date,
    val priority: Priority,
    val categoryId: Long? = null,
    val isCompleted: Boolean,
    val deletedAt: Date = Date()
) 