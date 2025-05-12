package org.haris0035.mobpro1.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.haris0035.mobpro1.data.model.Category

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryListScreen(
    categories: List<Category>,
    onAddCategory: () -> Unit,
    onEditCategory: (Long) -> Unit,
    onDeleteCategory: (Category) -> Unit,
    onNavigateBack: () -> Unit
) {
    var categoryToDelete by remember { mutableStateOf<Category?>(null) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Kategori") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddCategory
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah Kategori"
                )
            }
        }
    ) { paddingValues ->
        if (categories.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Belum ada kategori",
                    style = MaterialTheme.typography.titleLarge
                )
                
                Spacer(modifier = Modifier.padding(8.dp))
                
                Text(
                    text = "Klik tombol + untuk menambahkan kategori baru",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { category ->
                    CategoryItem(
                        category = category,
                        onEditClick = { onEditCategory(category.id) },
                        onDeleteClick = { categoryToDelete = category }
                    )
                }
            }
        }
    }
    
    // Delete confirmation dialog
    categoryToDelete?.let { category ->
        AlertDialog(
            title = { Text("Hapus Kategori") },
            text = { Text("Apakah Anda yakin ingin menghapus kategori \"${category.name}\"?") },
            onDismissRequest = { categoryToDelete = null },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteCategory(category)
                        categoryToDelete = null
                    }
                ) {
                    Text("Hapus")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { categoryToDelete = null }
                ) {
                    Text("Batal")
                }
            }
        )
    }
}

@Composable
fun CategoryItem(
    category: Category,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Color indicator
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(Color(category.color))
            )
            
            Spacer(modifier = Modifier.size(16.dp))
            
            // Category name
            Text(
                text = category.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            
            // Edit button
            IconButton(onClick = onEditClick) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit"
                )
            }
            
            // Delete button
            IconButton(onClick = onDeleteClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Hapus"
                )
            }
        }
    }
} 