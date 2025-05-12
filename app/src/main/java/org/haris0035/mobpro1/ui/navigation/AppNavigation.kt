package org.haris0035.mobpro1.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.haris0035.mobpro1.ui.screens.AddEditCategoryScreen
import org.haris0035.mobpro1.ui.screens.AddEditTaskScreen
import org.haris0035.mobpro1.ui.screens.CategoryListScreen
import org.haris0035.mobpro1.ui.screens.RecycleBinScreen
import org.haris0035.mobpro1.ui.screens.SettingsScreen
import org.haris0035.mobpro1.ui.screens.TaskDetailScreen
import org.haris0035.mobpro1.ui.screens.TaskListScreen
import org.haris0035.mobpro1.ui.viewmodel.SettingsViewModel
import org.haris0035.mobpro1.ui.viewmodel.TaskViewModel

sealed class Screen(val route: String) {
    object TaskList : Screen("taskList")
    object RecycleBin : Screen("recycleBin")
    object AddTask : Screen("addTask")
    object EditTask : Screen("editTask/{taskId}")
    object TaskDetail : Screen("taskDetail/{taskId}")
    object Settings : Screen("settings")
    object CategoryList : Screen("categoryList")
    object AddCategory : Screen("addCategory")
    object EditCategory : Screen("editCategory/{categoryId}")
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    viewModel: TaskViewModel,
    settingsViewModel: SettingsViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Screen.TaskList.route
    ) {
        composable(Screen.TaskList.route) {
            TaskListScreen(
                onNavigateToAddTask = {
                    navController.navigate(Screen.AddTask.route)
                },
                onNavigateToTaskDetail = { taskId ->
                    navController.navigate("taskDetail/$taskId")
                },
                onNavigateToRecycleBin = {
                    navController.navigate(Screen.RecycleBin.route)
                },
                onNavigateToSettings = {
                    navController.navigate(Screen.Settings.route)
                },
                onNavigateToCategories = {
                    navController.navigate(Screen.CategoryList.route)
                },
                viewModel = viewModel,
                settingsViewModel = settingsViewModel
            )
        }

        composable(Screen.RecycleBin.route) {
            RecycleBinScreen(
                deletedTasks = uiState.deletedTasks,
                isLoading = uiState.isLoading,
                error = uiState.error,
                onRestoreTask = { recycleBin ->
                    viewModel.restoreTask(recycleBin)
                },
                onPermanentlyDeleteTask = { recycleBin ->
                    viewModel.permanentlyDeleteTask(recycleBin)
                },
                onClearRecycleBin = {
                    viewModel.clearRecycleBin()
                },
                onNavigateBack = {
                    navController.popBackStack()
                },
                onErrorDismiss = {
                    viewModel.clearError()
                }
            )
        }
        
        composable(Screen.AddTask.route) {
            AddEditTaskScreen(
                onSaveTask = { task ->
                    viewModel.addTask(task)
                    navController.popBackStack()
                },
                onNavigateBack = {
                    navController.popBackStack()
                },
                viewModel = viewModel
            )
        }
        
        composable(
            route = Screen.EditTask.route,
            arguments = listOf(navArgument("taskId") { type = NavType.LongType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getLong("taskId") ?: 0L
            AddEditTaskScreen(
                taskId = taskId,
                onSaveTask = { task ->
                    viewModel.updateTask(task)
                    navController.popBackStack()
                },
                onNavigateBack = {
                    navController.popBackStack()
                },
                viewModel = viewModel
            )
        }
        
        composable(
            route = Screen.TaskDetail.route,
            arguments = listOf(navArgument("taskId") { type = NavType.LongType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getLong("taskId") ?: 0L
            TaskDetailScreen(
                taskId = taskId,
                onEditTask = { id ->
                    navController.navigate("editTask/$id")
                },
                onDeleteTask = { task ->
                    viewModel.deleteTask(task)
                    navController.popBackStack()
                },
                onNavigateBack = {
                    navController.popBackStack()
                },
                viewModel = viewModel
            )
        }
        
        composable(Screen.Settings.route) {
            SettingsScreen(
                settingsViewModel = settingsViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(Screen.CategoryList.route) {
            CategoryListScreen(
                categories = uiState.categories,
                onAddCategory = {
                    navController.navigate(Screen.AddCategory.route)
                },
                onEditCategory = { categoryId ->
                    navController.navigate("editCategory/$categoryId")
                },
                onDeleteCategory = { category ->
                    viewModel.deleteCategory(category)
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(Screen.AddCategory.route) {
            AddEditCategoryScreen(
                onSaveCategory = { category ->
                    viewModel.addCategory(category)
                    navController.popBackStack()
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(
            route = Screen.EditCategory.route,
            arguments = listOf(navArgument("categoryId") { type = NavType.LongType })
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getLong("categoryId") ?: 0L
            AddEditCategoryScreen(
                categoryId = categoryId,
                onSaveCategory = { category ->
                    viewModel.updateCategory(category)
                    navController.popBackStack()
                },
                onNavigateBack = {
                    navController.popBackStack()
                },
                viewModel = viewModel
            )
        }
    }
} 