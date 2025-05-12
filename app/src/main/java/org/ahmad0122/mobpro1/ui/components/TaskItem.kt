package org.ahmad0122.mobpro1.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.ahmad0122.mobpro1.data.model.Priority
import org.ahmad0122.mobpro1.data.model.Task
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskItem(
    task: Task,
    categoryName: String? = null,
    categoryColor: Int? = null,
    onTaskClick: () -> Unit,
    isGridView: Boolean = false,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    val dateFormat = remember { SimpleDateFormat("dd MMM yyyy", Locale.getDefault()) }

    Card(
        modifier = modifier.then(
            if (isGridView) Modifier.fillMaxWidth()
            else Modifier.fillMaxWidth()
        ),
        onClick = onTaskClick
    ) {
        if (isGridView) {
            // Grid layout
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Category color indicator
                    if (categoryColor != null) {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .clip(CircleShape)
                                .background(Color(categoryColor))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    
                    // Task title
                    Text(
                        text = task.title,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Task description
                Text(
                    text = task.description.ifBlank { "Tidak ada deskripsi" },
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Due date
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PriorityChip(priority = task.priority)
                    Text(
                        text = dateFormat.format(task.dueDate),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                
                // Category (if available)
                if (categoryName != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = categoryName,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        } else {
            // List layout
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Category color indicator
                    if (categoryColor != null) {
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .clip(CircleShape)
                                .background(Color(categoryColor))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    
                    // Task title
                    Text(
                        text = task.title,
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
                    text = task.description.ifBlank { "Tidak ada deskripsi" },
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
                        PriorityChip(priority = task.priority)
                        
                        // Category (if available)
                        if (categoryName != null) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Kategori: $categoryName",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                    
                    // Due date
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "Tenggat:",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = dateFormat.format(task.dueDate),
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PriorityChip(priority: Priority) {
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