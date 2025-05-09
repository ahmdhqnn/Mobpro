package org.panjirai0110.mobpro1.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import org.panjirai0110.mobpro1.data.entity.Task

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks WHERE isDeleted = 0")
    fun getAllTasks(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE isDeleted = 1")
    fun getDeletedTasks(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    suspend fun getTaskById(taskId: Int): Task?

    @Insert
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Query("UPDATE tasks SET isDeleted = 1, deletedAt = :timestamp WHERE id = :taskId")
    suspend fun softDeleteTask(taskId: Int, timestamp: Long = System.currentTimeMillis())

    @Query("UPDATE tasks SET isDeleted = 0, deletedAt = NULL WHERE id = :taskId")
    suspend fun restoreTask(taskId: Int)

    @Query("DELETE FROM tasks WHERE id = :taskId")
    suspend fun permanentlyDeleteTask(taskId: Int)

    @Query("DELETE FROM tasks WHERE isDeleted = 1")
    suspend fun clearRecycleBin()
} 