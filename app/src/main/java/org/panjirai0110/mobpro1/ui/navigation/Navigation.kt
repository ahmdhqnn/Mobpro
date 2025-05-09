package org.panjirai0110.mobpro1.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.panjirai0110.mobpro1.ui.screen.*
import org.panjirai0110.mobpro1.ui.viewmodel.TaskViewModel

sealed class Screen(val route: String) {
    object TaskList : Screen("tasks")
    object AddTask : Screen("add_task")
    object EditTask : Screen("edit_task/{taskId}") {
        fun createRoute(taskId: Int) = "edit_task/$taskId"
    }
    object RecycleBin : Screen("recycle_bin")
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    viewModel: TaskViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    
    NavHost(
        navController = navController,
        startDestination = Screen.TaskList.route
    ) {
        composable(Screen.TaskList.route) {
            TaskScreen(
                uiState = uiState,
                onAddTask = { navController.navigate(Screen.AddTask.route) },
                onTaskClick = { task ->
                    navController.navigate(Screen.EditTask.createRoute(task.id))
                },
                onDeleteTask = { taskId -> viewModel.deleteTask(taskId) },
                onViewTypeChange = { viewType -> viewModel.updateViewType(viewType) },
                onThemeChange = { isDark -> viewModel.updateTheme(isDark) },
                onNavigateToRecycleBin = { navController.navigate(Screen.RecycleBin.route) },
                onThemeColorChange = { color -> viewModel.updateThemeColor(color) }
            )
        }

        composable(Screen.AddTask.route) {
            EditTaskScreen(
                task = null,
                onSave = { title, description ->
                    viewModel.addTask(title, description)
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.EditTask.route,
            arguments = listOf(
                navArgument("taskId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt("taskId") ?: return@composable
            val task = uiState.tasks.find { it.id == taskId }
            
            // Gunakan getTaskById bila perlu
            if (task == null) {
                viewModel.getTaskById(taskId)
            }
            
            EditTaskScreen(
                task = task,
                onSave = { title, description ->
                    task?.copy(
                        title = title,
                        description = description,
                        updatedAt = System.currentTimeMillis()
                    )?.let { viewModel.updateTask(it) }
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.RecycleBin.route) {
            RecycleBinScreen(
                deletedTasks = uiState.deletedTasks,
                onRestoreTask = { taskId -> viewModel.restoreTask(taskId) },
                onPermanentlyDelete = { taskId -> viewModel.permanentlyDeleteTask(taskId) },
                onClearRecycleBin = { viewModel.clearRecycleBin() },
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
} 