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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.panjirai0110.mobpro1.data.entity.Task

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecycleBinScreen(
    deletedTasks: List<Task>,
    onRestoreTask: (Int) -> Unit,
    onPermanentlyDelete: (Int) -> Unit,
    onNavigateBack: () -> Unit,
    onClearRecycleBin: () -> Unit = {},
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    var taskToDelete by remember { mutableStateOf<Task?>(null) }
    var showClearConfirmation by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recycle Bin") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    if (deletedTasks.isNotEmpty()) {
                        IconButton(onClick = { showClearConfirmation = true }) {
                            Icon(Icons.Default.Delete, contentDescription = "Clear All")
                        }
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(deletedTasks) { task ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = task.title,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = task.description,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(
                                onClick = { onRestoreTask(task.id) }
                            ) {
                                Icon(Icons.Default.Refresh, contentDescription = null)
                                Spacer(Modifier.width(4.dp))
                                Text("Restore")
                            }
                            TextButton(
                                onClick = { taskToDelete = task }
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = null)
                                Spacer(Modifier.width(4.dp))
                                Text("Delete Permanently")
                            }
                        }
                    }
                }
            }
            
            if (deletedTasks.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Recycle Bin is empty",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }

        taskToDelete?.let { task ->
            AlertDialog(
                onDismissRequest = { taskToDelete = null },
                title = { Text("Delete Permanently") },
                text = { Text("This action cannot be undone. Are you sure you want to permanently delete '${task.title}'?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            onPermanentlyDelete(task.id)
                            taskToDelete = null
                        }
                    ) {
                        Text("Delete")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { taskToDelete = null }) {
                        Text("Cancel")
                    }
                }
            )
        }
        
        if (showClearConfirmation) {
            AlertDialog(
                onDismissRequest = { showClearConfirmation = false },
                title = { Text("Clear Recycle Bin") },
                text = { Text("This will permanently delete all items in the Recycle Bin. Are you sure?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            onClearRecycleBin()
                            showClearConfirmation = false
                        }
                    ) {
                        Text("Clear All")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showClearConfirmation = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
} 