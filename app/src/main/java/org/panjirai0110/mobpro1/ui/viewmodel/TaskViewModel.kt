package org.panjirai0110.mobpro1.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.panjirai0110.mobpro1.data.entity.Task
import org.panjirai0110.mobpro1.data.repository.TaskRepository
import org.panjirai0110.mobpro1.data.UserPreferences

data class TaskUiState(
    val tasks: List<Task> = emptyList(),
    val deletedTasks: List<Task> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val viewType: String = "list",
    val isDarkMode: Boolean = false,
    val themeColor: Int = 0xFF6200EE.toInt()
)

class TaskViewModel(
    private val repository: TaskRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {
    private val _uiState = MutableStateFlow(TaskUiState())
    val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                repository.getAllTasks(),
                repository.getDeletedTasks(),
                userPreferences.viewType,
                userPreferences.isDarkMode,
                userPreferences.themeColor
            ) { tasks, deletedTasks, viewType, isDarkMode, themeColor ->
                _uiState.value = _uiState.value.copy(
                    tasks = tasks,
                    deletedTasks = deletedTasks,
                    viewType = viewType,
                    isDarkMode = isDarkMode,
                    themeColor = themeColor
                )
            }.collect()
        }
    }

    fun addTask(title: String, description: String) {
        viewModelScope.launch {
            try {
                val task = Task(title = title, description = description)
                repository.insertTask(task)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            try {
                repository.updateTask(task)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }

    fun deleteTask(taskId: Int) {
        viewModelScope.launch {
            try {
                repository.softDeleteTask(taskId)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }

    fun restoreTask(taskId: Int) {
        viewModelScope.launch {
            try {
                repository.restoreTask(taskId)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }

    fun permanentlyDeleteTask(taskId: Int) {
        viewModelScope.launch {
            try {
                repository.permanentlyDeleteTask(taskId)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }
    
    fun clearRecycleBin() {
        viewModelScope.launch {
            try {
                repository.clearRecycleBin()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }
    
    fun getTaskById(taskId: Int) {
        viewModelScope.launch {
            try {
                val task = repository.getTaskById(taskId)
                // Jika task ditemukan, tambahkan ke daftar tugas sementara
                if (task != null && !_uiState.value.tasks.any { it.id == task.id }) {
                    _uiState.value = _uiState.value.copy(
                        tasks = _uiState.value.tasks + task
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }

    fun updateViewType(viewType: String) {
        viewModelScope.launch {
            userPreferences.setViewType(viewType)
        }
    }

    fun updateTheme(isDark: Boolean) {
        viewModelScope.launch {
            userPreferences.setDarkMode(isDark)
        }
    }

    fun updateThemeColor(color: Int) {
        viewModelScope.launch {
            userPreferences.setThemeColor(color)
        }
    }

    class Factory(
        private val repository: TaskRepository,
        private val userPreferences: UserPreferences
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return TaskViewModel(repository, userPreferences) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
} 