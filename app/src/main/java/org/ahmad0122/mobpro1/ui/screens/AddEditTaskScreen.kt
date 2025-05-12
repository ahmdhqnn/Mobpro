package org.ahmad0122.mobpro1.ui.screens

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import org.ahmad0122.mobpro1.data.model.Priority
import org.ahmad0122.mobpro1.data.model.Task
import org.ahmad0122.mobpro1.ui.viewmodel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTaskScreen(
    taskId: Long = 0L,
    onSaveTask: (Task) -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: TaskViewModel
) {
    val isEditMode = taskId > 0
    val task = if (isEditMode) viewModel.getTaskById(taskId) else null
    val uiState by viewModel.uiState.collectAsState()
    
    var title by remember { mutableStateOf(task?.title ?: "") }
    var description by remember { mutableStateOf(task?.description ?: "") }
    var dueDate by remember { mutableStateOf(task?.dueDate ?: Calendar.getInstance().time) }
    var priority by remember { mutableStateOf(task?.priority ?: Priority.MEDIUM) }
    var categoryId by remember { mutableStateOf(task?.categoryId) }
    
    val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
    var showPriorityDropdown by remember { mutableStateOf(false) }
    var showCategoryDropdown by remember { mutableStateOf(false) }
    
    // Untuk DatePicker
    val context = LocalContext.current
    
    // Kalender untuk manipulasi tanggal
    val calendar = Calendar.getInstance()
    calendar.time = dueDate
    
    // DatePickerDialog
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            dueDate = calendar.time
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    
    // Cek jika task ditemukan saat mode edit
    LaunchedEffect(taskId) {
        if (isEditMode && task == null) {
            onNavigateBack()
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditMode) "Edit Tugas" else "Tambah Tugas") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            if (title.isNotBlank()) {
                                val updatedTask = if (isEditMode && task != null) {
                                    task.copy(
                                        title = title.trim(),
                                        description = description.trim(),
                                        dueDate = dueDate,
                                        priority = priority,
                                        categoryId = categoryId,
                                        updatedAt = Date()
                                    )
                                } else {
                                    Task(
                                        title = title.trim(),
                                        description = description.trim(),
                                        dueDate = dueDate,
                                        priority = priority,
                                        categoryId = categoryId
                                    )
                                }
                                onSaveTask(updatedTask)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Simpan"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Title
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Judul") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            // Description
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Deskripsi") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                maxLines = 5
            )
            
            // Due Date
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Tanggal Tenggat:",
                    style = MaterialTheme.typography.bodyLarge
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                TextButton(
                    onClick = {
                        // Tampilkan DatePickerDialog
                        datePickerDialog.show()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null
                    )
                    
                    Spacer(modifier = Modifier.width(4.dp))
                    
                    Text(dateFormat.format(dueDate))
                }
            }
            
            // Priority
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Prioritas:",
                    style = MaterialTheme.typography.bodyLarge
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                TextButton(
                    onClick = { showPriorityDropdown = true }
                ) {
                    Text(priority.name)
                    
                    Spacer(modifier = Modifier.width(4.dp))
                    
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null
                    )
                    
                    DropdownMenu(
                        expanded = showPriorityDropdown,
                        onDismissRequest = { showPriorityDropdown = false }
                    ) {
                        Priority.entries.forEach { priorityOption ->
                            DropdownMenuItem(
                                text = { Text(priorityOption.name) },
                                onClick = {
                                    priority = priorityOption
                                    showPriorityDropdown = false
                                }
                            )
                        }
                    }
                }
            }
            
            // Category
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Kategori:",
                    style = MaterialTheme.typography.bodyLarge
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                TextButton(
                    onClick = { showCategoryDropdown = true }
                ) {
                    Text(
                        text = categoryId?.let { id -> 
                            uiState.categories.find { it.id == id }?.name 
                        } ?: "Tidak ada"
                    )
                    
                    Spacer(modifier = Modifier.width(4.dp))
                    
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null
                    )
                    
                    DropdownMenu(
                        expanded = showCategoryDropdown,
                        onDismissRequest = { showCategoryDropdown = false }
                    ) {
                        // No category option
                        DropdownMenuItem(
                            text = { Text("Tidak ada") },
                            onClick = {
                                categoryId = null
                                showCategoryDropdown = false
                            }
                        )
                        
                        // Category options
                        uiState.categories.forEach { category ->
                            DropdownMenuItem(
                                text = { Text(category.name) },
                                onClick = {
                                    categoryId = category.id
                                    showCategoryDropdown = false
                                }
                            )
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        val updatedTask = if (isEditMode && task != null) {
                            task.copy(
                                title = title.trim(),
                                description = description.trim(),
                                dueDate = dueDate,
                                priority = priority,
                                categoryId = categoryId,
                                updatedAt = Date()
                            )
                        } else {
                            Task(
                                title = title.trim(),
                                description = description.trim(),
                                dueDate = dueDate,
                                priority = priority,
                                categoryId = categoryId
                            )
                        }
                        onSaveTask(updatedTask)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isEditMode) "Perbarui" else "Simpan")
            }
        }
    }
} 