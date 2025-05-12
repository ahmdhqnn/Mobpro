package org.haris0035.mobpro1.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.haris0035.mobpro1.ui.components.TaskItem
import org.haris0035.mobpro1.ui.viewmodel.SettingsViewModel
import org.haris0035.mobpro1.ui.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    onNavigateToAddTask: () -> Unit,
    onNavigateToTaskDetail: (Long) -> Unit,
    onNavigateToRecycleBin: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToCategories: () -> Unit,
    viewModel: TaskViewModel,
    settingsViewModel: SettingsViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val listViewType by settingsViewModel.listViewType.collectAsState(initial = "list")
    var showErrorDialog by remember { mutableStateOf(false) }
    var selectedCategoryId by remember { mutableStateOf<Long?>(null) }

    // Debug output untuk melihat perubahan tampilan
    LaunchedEffect(listViewType) {
        println("View type changed to: $listViewType")
    }

    LaunchedEffect(uiState.error) {
        if (uiState.error != null) {
            showErrorDialog = true
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Daftar Tugas") },
                actions = {
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(Icons.Default.Settings, contentDescription = "Pengaturan")
                    }
                    IconButton(onClick = onNavigateToCategories) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Kategori")
                    }
                    IconButton(onClick = onNavigateToRecycleBin) {
                        Icon(Icons.Default.Delete, contentDescription = "Tempat Sampah")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToAddTask) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Tugas")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Category filter
            if (uiState.categories.isNotEmpty()) {
                ScrollableTabRow(
                    selectedTabIndex = uiState.categories.indexOfFirst { it.id == selectedCategoryId } + 1,
                    edgePadding = 16.dp
                ) {
                    // All categories option
                    Tab(
                        selected = selectedCategoryId == null,
                        onClick = { selectedCategoryId = null },
                        text = { Text("Semua") }
                    )
                    
                    // Individual categories
                    uiState.categories.forEach { category ->
                        Tab(
                            selected = selectedCategoryId == category.id,
                            onClick = { selectedCategoryId = category.id },
                            text = { Text(category.name) }
                        )
                    }
                }
            }
            
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                val filteredTasks = if (selectedCategoryId != null) {
                    uiState.tasks.filter { it.categoryId == selectedCategoryId }
                } else {
                    uiState.tasks
                }
                
                if (filteredTasks.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Belum ada tugas. Tambahkan satu!",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                } else {
                    when (listViewType) {
                        "grid" -> {
                            // Grid View
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(16.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(filteredTasks) { task ->
                                    TaskItem(
                                        task = task,
                                        categoryName = task.categoryId?.let { id ->
                                            uiState.categories.find { it.id == id }?.name
                                        },
                                        categoryColor = task.categoryId?.let { id ->
                                            uiState.categories.find { it.id == id }?.color
                                        },
                                        onTaskClick = { onNavigateToTaskDetail(task.id) },
                                        isGridView = true
                                    )
                                }
                            }
                        }
                        "list" -> {
                            // List View
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(filteredTasks) { task ->
                                    TaskItem(
                                        task = task,
                                        categoryName = task.categoryId?.let { id ->
                                            uiState.categories.find { it.id == id }?.name
                                        },
                                        categoryColor = task.categoryId?.let { id ->
                                            uiState.categories.find { it.id == id }?.color
                                        },
                                        onTaskClick = { onNavigateToTaskDetail(task.id) },
                                        isGridView = false
                                    )
                                }
                            }
                        }
                        else -> {
                            // Fallback jika tipe tampilan tidak valid
                            Text(
                                text = "Tipe tampilan tidak valid: $listViewType. Harap atur kembali tampilan di pengaturan.",
                                modifier = Modifier.padding(16.dp),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }
    }

    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = {
                showErrorDialog = false
                viewModel.clearError()
            },
            title = { Text("Error") },
            text = { Text(uiState.error ?: "Terjadi kesalahan yang tidak diketahui") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showErrorDialog = false
                        viewModel.clearError()
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }
} 