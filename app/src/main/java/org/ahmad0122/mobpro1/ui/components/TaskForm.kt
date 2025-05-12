package org.ahmad0122.mobpro1.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.ahmad0122.mobpro1.data.model.Priority
import org.ahmad0122.mobpro1.data.model.Task
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskForm(
    task: Task? = null,
    onSave: (String, String, Date, Priority) -> Unit,
    onDismiss: () -> Unit,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    var title by remember { mutableStateOf(task?.title ?: "") }
    var description by remember { mutableStateOf(task?.description ?: "") }
    var dueDate by remember { mutableStateOf(task?.dueDate ?: Date()) }
    var priority by remember { mutableStateOf(task?.priority ?: Priority.MEDIUM) }
    var showDatePicker by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = if (task == null) "Add New Task" else "Edit Task",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(onClick = { showDatePicker = true }) {
                Text("Due Date: ${formatDate(dueDate)}")
            }

            PrioritySelector(
                selectedPriority = priority,
                onPrioritySelected = { priority = it }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        onSave(title, description, dueDate, priority)
                    }
                },
                enabled = title.isNotBlank()
            ) {
                Text(if (task == null) "Add" else "Save")
            }
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("OK")
                }
            }
        ) {
            DatePicker(
                state = rememberDatePickerState(initialSelectedDateMillis = dueDate.time)
            )
        }
    }
}

@Composable
fun PrioritySelector(
    selectedPriority: Priority,
    onPrioritySelected: (Priority) -> Unit
) {
    Row {
        Priority.entries.forEach { priority ->
            FilterChip(
                selected = priority == selectedPriority,
                onClick = { onPrioritySelected(priority) },
                label = { Text(priority.name) }
            )
            Spacer(modifier = Modifier.width(4.dp))
        }
    }
}

private fun formatDate(date: Date): String {
    val formatter = java.text.SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return formatter.format(date)
} 