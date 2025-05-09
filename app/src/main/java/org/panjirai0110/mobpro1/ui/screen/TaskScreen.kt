package org.panjirai0110.mobpro1.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.panjirai0110.mobpro1.data.entity.Task
import org.panjirai0110.mobpro1.ui.viewmodel.TaskUiState
import org.panjirai0110.mobpro1.R
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(
    uiState: TaskUiState,
    onAddTask: () -> Unit,
    onTaskClick: (Task) -> Unit,
    onDeleteTask: (Int) -> Unit,
    onViewTypeChange: (String) -> Unit,
    onThemeChange: (Boolean) -> Unit,
    onNavigateToRecycleBin: () -> Unit,
    onThemeColorChange: (Int) -> Unit = {},
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    var showDeleteDialog by remember { mutableStateOf<Task?>(null) }
    var showColorPicker by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tasks") },
                actions = {
                    IconButton(onClick = { onViewTypeChange(if (uiState.viewType == "list") "grid" else "list") }) {
                        Icon(
                            painter = painterResource(
                                if (uiState.viewType == "list") R.drawable.baseline_grid_view_24 else R.drawable.baseline_view_list_24
                            ),
                            contentDescription = "Toggle view"
                        )
                    }
                    IconButton(onClick = { onThemeChange(!uiState.isDarkMode) }) {
                        Icon(
                            painter = painterResource(
                                if (uiState.isDarkMode) R.drawable.baseline_light_mode_24 else R.drawable.baseline_dark_mode_24
                            ),
                            contentDescription = "Toggle theme"
                        )
                    }
                    IconButton(onClick = { showColorPicker = true }) {
                        Icon(
                            painter = painterResource(
                                R.drawable.baseline_color_lens_24
                            ),
                            contentDescription = "Change Theme Color"
                        )
                    }
                    IconButton(onClick = onNavigateToRecycleBin) {
                        Icon(
                            Icons.Outlined.Delete,
                            contentDescription = "Recycle Bin"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddTask) {
                Icon(Icons.Default.Add, contentDescription = "Add task")
            }
        }
    ) { padding ->
        if (uiState.viewType == "list") {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.tasks) { task ->
                    TaskItem(
                        task = task,
                        onClick = { onTaskClick(task) },
                        onDelete = { showDeleteDialog = task }
                    )
                }
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.tasks) { task ->
                    TaskItem(
                        task = task,
                        onClick = { onTaskClick(task) },
                        onDelete = { showDeleteDialog = task }
                    )
                }
            }
        }

        showDeleteDialog?.let { task ->
            AlertDialog(
                onDismissRequest = { showDeleteDialog = null },
                title = { Text("Delete Task") },
                text = { Text("Are you sure you want to delete '${task.title}'?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            onDeleteTask(task.id)
                            showDeleteDialog = null
                        }
                    ) {
                        Text("Delete")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteDialog = null }) {
                        Text("Cancel")
                    }
                }
            )
        }
        
        if (showColorPicker) {
            ColorPickerDialog(
                currentColor = uiState.themeColor,
                onDismiss = { showColorPicker = false },
                onColorSelected = { 
                    onThemeColorChange(it)
                    showColorPicker = false
                }
            )
        }
    }
}

@Composable
fun ColorPickerDialog(
    currentColor: Int,
    onDismiss: () -> Unit,
    onColorSelected: (Int) -> Unit
) {
    var redValue by remember { mutableFloatStateOf((Color(currentColor).red * 255).roundToInt().toFloat()) }
    var greenValue by remember { mutableFloatStateOf((Color(currentColor).green * 255).roundToInt().toFloat()) }
    var blueValue by remember { mutableFloatStateOf((Color(currentColor).blue * 255).roundToInt().toFloat()) }
    
    val previewColor = Color(
        red = redValue.toInt(),
        green = greenValue.toInt(),
        blue = blueValue.toInt(),
        alpha = 255
    )
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Pick Theme Color") },
        text = {
            Column {
                // Preview color
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = CardDefaults.cardColors(containerColor = previewColor)
                ) { }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Red slider
                Text("Red: ${redValue.toInt()}")
                Slider(
                    value = redValue,
                    onValueChange = { redValue = it },
                    valueRange = 0f..255f,
                    steps = 255,
                    modifier = Modifier.fillMaxWidth()
                )
                
                // Green slider
                Text("Green: ${greenValue.toInt()}")
                Slider(
                    value = greenValue,
                    onValueChange = { greenValue = it },
                    valueRange = 0f..255f,
                    steps = 255,
                    modifier = Modifier.fillMaxWidth()
                )
                
                // Blue slider
                Text("Blue: ${blueValue.toInt()}")
                Slider(
                    value = blueValue,
                    onValueChange = { blueValue = it },
                    valueRange = 0f..255f,
                    steps = 255,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val colorInt = android.graphics.Color.rgb(
                        redValue.toInt(),
                        greenValue.toInt(),
                        blueValue.toInt()
                    )
                    onColorSelected(colorInt)
                }
            ) {
                Text("Apply")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskItem(
    task: Task,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleMedium
                )
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete task")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = task.description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
} 