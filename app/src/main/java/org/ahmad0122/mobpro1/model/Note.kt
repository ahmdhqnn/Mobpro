package org.ahmad0122.mobpro1.model

import java.util.Date

data class Note(
    val id: Long = System.currentTimeMillis(),
    val content: String,
    val timestamp: Date = Date(),
    val category: String = "Personal",
    val isImportant: Boolean = false
) 