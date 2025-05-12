package org.haris0035.mobpro1.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import org.haris0035.mobpro1.data.model.Category
import org.haris0035.mobpro1.ui.theme.Blue
import org.haris0035.mobpro1.ui.theme.Green
import org.haris0035.mobpro1.ui.theme.Orange
import org.haris0035.mobpro1.ui.theme.Purple
import org.haris0035.mobpro1.ui.theme.Red
import org.haris0035.mobpro1.ui.viewmodel.TaskViewModel
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditCategoryScreen(
    categoryId: Long = 0L,
    onSaveCategory: (Category) -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: TaskViewModel? = null
) {
    val isEditMode = categoryId > 0
    val category = viewModel?.getCategoryById(categoryId)
    
    var name by remember { mutableStateOf(category?.name ?: "") }
    var selectedColor by remember { mutableIntStateOf(category?.color ?: Green.toArgb()) }
    
    // Available colors for selection
    val colorOptions = listOf(
        Green.toArgb(),
        Blue.toArgb(),
        Purple.toArgb(),
        Orange.toArgb(),
        Red.toArgb()
    )
    
    // Periksa jika kategori ditemukan saat mode edit
    LaunchedEffect(categoryId) {
        if (isEditMode && category == null) {
            onNavigateBack()
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditMode) "Edit Kategori" else "Tambah Kategori") },
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
                            if (name.isNotBlank()) {
                                val updatedCategory = if (isEditMode && category != null) {
                                    category.copy(
                                        name = name.trim(),
                                        color = selectedColor,
                                        updatedAt = Date()
                                    )
                                } else {
                                    Category(
                                        name = name.trim(),
                                        color = selectedColor
                                    )
                                }
                                onSaveCategory(updatedCategory)
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Category name
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nama Kategori") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            // Color selection
            Text(
                text = "Warna Kategori:",
                style = MaterialTheme.typography.titleMedium
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                colorOptions.forEach { color ->
                    ColorSelectionItem(
                        color = Color(color),
                        isSelected = selectedColor == color,
                        onSelect = { selectedColor = color }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = {
                    if (name.isNotBlank()) {
                        val updatedCategory = if (isEditMode && category != null) {
                            category.copy(
                                name = name.trim(),
                                color = selectedColor,
                                updatedAt = Date()
                            )
                        } else {
                            Category(
                                name = name.trim(),
                                color = selectedColor
                            )
                        }
                        onSaveCategory(updatedCategory)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isEditMode) "Perbarui" else "Simpan")
            }
        }
    }
}

@Composable
fun ColorSelectionItem(
    color: Color,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(color)
            .clickable { onSelect() }
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        if (isSelected) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.5f))
            )
        }
    }
} 