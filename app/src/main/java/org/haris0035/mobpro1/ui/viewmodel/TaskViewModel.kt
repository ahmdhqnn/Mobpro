package org.haris0035.mobpro1.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.haris0035.mobpro1.data.model.Category
import org.haris0035.mobpro1.data.model.RecycleBin
import org.haris0035.mobpro1.data.model.Task
import org.haris0035.mobpro1.data.repository.TaskRepository

data class TaskUiState(
    val tasks: List<Task> = emptyList(),
    val deletedTasks: List<RecycleBin> = emptyList(),
    val categories: List<Category> = emptyList(),
    val selectedTask: Task? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(TaskUiState())
    val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            try {
                repository.allTasks.collect { tasks ->
                    _uiState.update { it.copy(tasks = tasks, isLoading = false) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }

        viewModelScope.launch {
            try {
                repository.allDeletedTasks.collect { deletedTasks ->
                    _uiState.update { it.copy(deletedTasks = deletedTasks) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
        
        viewModelScope.launch {
            try {
                repository.allCategories.collect { categories ->
                    _uiState.update { it.copy(categories = categories) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    fun addTask(task: Task) {
        if (task.title.isBlank()) {
            _uiState.update { it.copy(error = "Title cannot be empty") }
            return
        }

        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                repository.insertTask(task)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun updateTask(task: Task) {
        if (task.title.isBlank()) {
            _uiState.update { it.copy(error = "Title cannot be empty") }
            return
        }

        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                repository.updateTask(task)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                repository.deleteTask(task)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun restoreTask(recycleBin: RecycleBin) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                repository.restoreTask(recycleBin)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun permanentlyDeleteTask(recycleBin: RecycleBin) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                repository.permanentlyDeleteTask(recycleBin)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun clearRecycleBin() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                repository.clearRecycleBin()
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun setSelectedTask(task: Task?) {
        _uiState.update { it.copy(selectedTask = task) }
    }

    fun getTaskById(taskId: Long): Task? {
        return uiState.value.tasks.find { it.id == taskId }
    }
    
    // Category Operations
    fun addCategory(category: Category) {
        if (category.name.isBlank()) {
            _uiState.update { it.copy(error = "Category name cannot be empty") }
            return
        }

        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                repository.insertCategory(category)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun updateCategory(category: Category) {
        if (category.name.isBlank()) {
            _uiState.update { it.copy(error = "Category name cannot be empty") }
            return
        }

        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                repository.updateCategory(category)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun deleteCategory(category: Category) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                repository.deleteCategory(category)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
    
    fun getCategoryById(categoryId: Long): Category? {
        return uiState.value.categories.find { it.id == categoryId }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}

class TaskViewModelFactory(private val repository: TaskRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
} 