package org.haris0035.mobpro1.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import org.haris0035.mobpro1.data.model.RecycleBin

@Dao
interface RecycleBinDao {
    @Query("SELECT * FROM recycle_bin ORDER BY deletedAt DESC")
    fun getAllDeletedTasks(): Flow<List<RecycleBin>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeletedTask(task: RecycleBin)

    @Delete
    suspend fun permanentlyDeleteTask(task: RecycleBin)

    @Query("DELETE FROM recycle_bin")
    suspend fun clearRecycleBin()
} 