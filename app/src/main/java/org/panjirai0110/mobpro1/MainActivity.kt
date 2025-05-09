package org.panjirai0110.mobpro1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import org.panjirai0110.mobpro1.data.AppDatabase
import org.panjirai0110.mobpro1.data.UserPreferences
import org.panjirai0110.mobpro1.data.repository.TaskRepository
import org.panjirai0110.mobpro1.ui.navigation.AppNavigation
import org.panjirai0110.mobpro1.ui.theme.MobproTheme
import org.panjirai0110.mobpro1.ui.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getDatabase(this)
        val repository = TaskRepository(database.taskDao())
        val userPreferences = UserPreferences(this)

        setContent {
            val navController = rememberNavController()
            val viewModel: TaskViewModel = viewModel(
                factory = TaskViewModel.Factory(repository, userPreferences)
            )
            val uiState by viewModel.uiState.collectAsState()

            MobproTheme(
                theme = if (uiState.isDarkMode) 1 else 0
            ) {
                Surface {
                    AppNavigation(
                        navController = navController,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}