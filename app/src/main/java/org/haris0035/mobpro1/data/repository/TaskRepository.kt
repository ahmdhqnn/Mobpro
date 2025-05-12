package org.haris0035.mobpro1.data.repository

import kotlinx.coroutines.flow.Flow
import org.haris0035.mobpro1.data.dao.CategoryDao
import org.haris0035.mobpro1.data.dao.RecycleBinDao
import org.haris0035.mobpro1.data.dao.TaskDao
import org.haris0035.mobpro1.data.model.Category
import org.haris0035.mobpro1.data.model.RecycleBin
import org.haris0035.mobpro1.data.model.Task

class TaskRepository(
    private val taskDao: TaskDao,
    private val recycleBinDao: RecycleBinDao,
    private val categoryDao: CategoryDao
) {
    val allTasks: Flow<List<Task>> = taskDao.getAllTasks()
    val allDeletedTasks: Flow<List<RecycleBin>> = recycleBinDao.getAllDeletedTasks()
    val allCategories: Flow<List<Category>> = categoryDao.getAllCategories()

    suspend fun insertTask(task: Task): Long {
        return taskDao.insertTask(task)
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: Task) {
        // Pindahkan task ke recycle bin sebelum dihapus
        val deletedTask = RecycleBin(
            taskId = task.id,
            title = task.title,
            description = task.description,
            dueDate = task.dueDate,
            priority = task.priority,
            categoryId = task.categoryId,
            isCompleted = task.isCompleted
        )
        recycleBinDao.insertDeletedTask(deletedTask)
        taskDao.deleteTask(task)
    }

    suspend fun restoreTask(recycleBin: RecycleBin) {
        val task = Task(
            id = recycleBin.taskId,
            title = recycleBin.title,
            description = recycleBin.description,
            dueDate = recycleBin.dueDate,
            priority = recycleBin.priority,
            categoryId = recycleBin.categoryId,
            isCompleted = recycleBin.isCompleted
        )
        taskDao.insertTask(task)
        recycleBinDao.permanentlyDeleteTask(recycleBin)
    }

    suspend fun permanentlyDeleteTask(recycleBin: RecycleBin) {
        recycleBinDao.permanentlyDeleteTask(recycleBin)
    }

    suspend fun clearRecycleBin() {
        recycleBinDao.clearRecycleBin()
    }

    fun getTasksByCompletionStatus(isCompleted: Boolean): Flow<List<Task>> {
        return taskDao.getTasksByCompletionStatus(isCompleted)
    }

    // Category operations
    suspend fun insertCategory(category: Category): Long {
        return categoryDao.insertCategory(category)
    }

    suspend fun updateCategory(category: Category) {
        categoryDao.updateCategory(category)
    }

    suspend fun deleteCategory(category: Category) {
        categoryDao.deleteCategory(category)
    }

    suspend fun getCategoryById(id: Long): Category? {
        return categoryDao.getCategoryById(id)
    }
} 