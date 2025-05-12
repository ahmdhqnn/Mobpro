package org.ahmad0122.mobpro1.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.ahmad0122.mobpro1.data.model.Priority
import org.ahmad0122.mobpro1.data.model.RecycleBin
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecycleBinScreen(
    deletedTasks: List<RecycleBin>,
    isLoading: Boolean,
    error: String?,
    onRestoreTask: (RecycleBin) -> Unit,
    onPermanentlyDeleteTask: (RecycleBin) -> Unit,
    onClearRecycleBin: () -> Unit,
    onNavigateBack: () -> Unit,
    onErrorDismiss: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    var showClearConfirmDialog by remember { mutableStateOf(false) }
    var selectedTaskForDelete by remember { mutableStateOf<RecycleBin?>(null) }

    LaunchedEffect(error) {
        if (error != null) {
            snackbarHostState.showSnackbar(error)
            onErrorDismiss()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Tempat Sampah") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali"
                        )
                    }
                },
                actions = {
                    if (deletedTasks.isNotEmpty()) {
                        IconButton(onClick = { showClearConfirmDialog = true }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Kosongkan Tempat Sampah"
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else if (deletedTasks.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Tempat Sampah Kosong",
                        style = MaterialTheme.typography.titleLarge
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                            Text(
                        text = "Item yang dihapus akan muncul di sini",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    } else {
                        LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                    items(deletedTasks) { recycleBin ->
                        DeletedTaskItem(
                            recycleBin = recycleBin,
                            onRestoreClick = { onRestoreTask(recycleBin) },
                            onDeleteClick = { selectedTaskForDelete = recycleBin }
                        )
                    }
                }
            }
        }
    }

    // Clear confirmation dialog
    if (showClearConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showClearConfirmDialog = false },
            title = { Text("Kosongkan Tempat Sampah") },
            text = { Text("Apakah Anda yakin ingin menghapus semua item secara permanen? Tindakan ini tidak dapat dibatalkan.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onClearRecycleBin()
                        showClearConfirmDialog = false
                    }
                ) {
                    Text("Kosongkan")
                }
            },
            dismissButton = {
                TextButton(onClick = { showClearConfirmDialog = false }) {
                    Text("Batal")
                }
            }
        )
    }

    // Delete confirmation dialog
    selectedTaskForDelete?.let { recycleBin ->
        AlertDialog(
            onDismissRequest = { selectedTaskForDelete = null },
            title = { Text("Hapus Permanen") },
            text = { Text("Apakah Anda yakin ingin menghapus permanen \"${recycleBin.title}\"? Tindakan ini tidak dapat dibatalkan.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onPermanentlyDeleteTask(recycleBin)
                        selectedTaskForDelete = null
                    }
                ) {
                    Text("Hapus")
                }
            },
            dismissButton = {
                TextButton(onClick = { selectedTaskForDelete = null }) {
                    Text("Batal")
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeletedTaskItem(
    recycleBin: RecycleBin,
    onRestoreClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val dateFormat = remember { SimpleDateFormat("dd MMM yyyy", Locale.getDefault()) }

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Indicator for deleted item
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.error)
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                // Task title
                Text(
                    text = recycleBin.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Task description
            Text(
                text = recycleBin.description.ifBlank { "Tidak ada deskripsi" },
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    // Priority
                    RecycleBinPriorityChip(priority = recycleBin.priority)
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    // Deleted date
                    Text(
                        text = "Dihapus: ${dateFormat.format(recycleBin.deletedAt)}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                
                // Action buttons
                Row {
                    IconButton(onClick = onRestoreClick) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Kembalikan"
                        )
                    }
                    
                    IconButton(onClick = onDeleteClick) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Hapus Permanen"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RecycleBinPriorityChip(priority: Priority) {
    val (color, text) = when (priority) {
        Priority.HIGH -> MaterialTheme.colorScheme.error to "Tinggi"
        Priority.MEDIUM -> MaterialTheme.colorScheme.tertiary to "Sedang"
        Priority.LOW -> MaterialTheme.colorScheme.primary to "Rendah"
    }

    Surface(
        color = color.copy(alpha = 0.1f),
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.bodySmall,
            color = color
        )
    }
} 