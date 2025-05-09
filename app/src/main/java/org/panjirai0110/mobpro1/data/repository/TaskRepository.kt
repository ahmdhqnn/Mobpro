package org.panjirai0110.mobpro1.data.repository

import kotlinx.coroutines.flow.Flow
import org.panjirai0110.mobpro1.data.dao.TaskDao
import org.panjirai0110.mobpro1.data.entity.Task

class TaskRepository(private val taskDao: TaskDao) {
    fun getAllTasks(): Flow<List<Task>> = taskDao.getAllTasks()
    
    fun getDeletedTasks(): Flow<List<Task>> = taskDao.getDeletedTasks()
    
    suspend fun getTaskById(id: Int): Task? = taskDao.getTaskById(id)
    
    suspend fun insertTask(task: Task) = taskDao.insertTask(task)
    
    suspend fun updateTask(task: Task) = taskDao.updateTask(task)
    
    suspend fun softDeleteTask(taskId: Int) = taskDao.softDeleteTask(taskId)
    
    suspend fun restoreTask(taskId: Int) = taskDao.restoreTask(taskId)
    
    suspend fun permanentlyDeleteTask(taskId: Int) = taskDao.permanentlyDeleteTask(taskId)
    
    suspend fun clearRecycleBin() = taskDao.clearRecycleBin()
} 