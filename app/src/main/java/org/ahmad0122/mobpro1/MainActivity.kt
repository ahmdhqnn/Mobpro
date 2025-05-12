package org.ahmad0122.mobpro1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import org.ahmad0122.mobpro1.data.AppDatabase
import org.ahmad0122.mobpro1.data.preferences.UserPreferences
import org.ahmad0122.mobpro1.data.repository.TaskRepository
import org.ahmad0122.mobpro1.ui.navigation.AppNavigation
import org.ahmad0122.mobpro1.ui.theme.TaskListTheme
import org.ahmad0122.mobpro1.ui.viewmodel.SettingsViewModel
import org.ahmad0122.mobpro1.ui.viewmodel.SettingsViewModelFactory
import org.ahmad0122.mobpro1.ui.viewmodel.TaskViewModel
import org.ahmad0122.mobpro1.ui.viewmodel.TaskViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getDatabase(applicationContext)
        val repository = TaskRepository(
            database.taskDao(),
            database.recycleBinDao(),
            database.categoryDao()
        )
        val userPreferences = UserPreferences(applicationContext)

        setContent {
            val settingsViewModel: SettingsViewModel = viewModel(
                factory = SettingsViewModelFactory(userPreferences)
            )
            
            val isDarkMode by settingsViewModel.isDarkMode.collectAsState(initial = isSystemInDarkTheme())
            val themeColor by settingsViewModel.themeColor.collectAsState(initial = 0)
            
            TaskListTheme(
                darkTheme = isDarkMode,
                themeColor = themeColor
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val viewModel: TaskViewModel = viewModel(
                        factory = TaskViewModelFactory(repository)
                    )

                    AppNavigation(
                        navController = navController,
                        viewModel = viewModel,
                        settingsViewModel = settingsViewModel
                    )
                }
            }
        }
    }
}
