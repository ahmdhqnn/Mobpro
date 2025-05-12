package org.haris0035.mobpro1.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import org.haris0035.mobpro1.data.model.Task

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks ORDER BY dueDate ASC")
    fun getAllTasks(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE id = :id")
    suspend fun getTaskById(id: Long): Task?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task): Long

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM tasks WHERE isCompleted = :isCompleted ORDER BY dueDate ASC")
    fun getTasksByCompletionStatus(isCompleted: Boolean): Flow<List<Task>>
} 