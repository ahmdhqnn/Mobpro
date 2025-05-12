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
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.haris0035.mobpro1.ui.theme.Blue
import org.haris0035.mobpro1.ui.theme.Green
import org.haris0035.mobpro1.ui.theme.Orange
import org.haris0035.mobpro1.ui.theme.Purple
import org.haris0035.mobpro1.ui.theme.Red
import org.haris0035.mobpro1.ui.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    settingsViewModel: SettingsViewModel,
    onNavigateBack: () -> Unit
) {
    val isDarkMode by settingsViewModel.isDarkMode.collectAsState(initial = false)
    val themeColor by settingsViewModel.themeColor.collectAsState(initial = 0)
    val listViewType by settingsViewModel.listViewType.collectAsState(initial = "list")
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pengaturan") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali"
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
            // Dark mode switch
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Mode Gelap",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Switch(
                        checked = isDarkMode,
                        onCheckedChange = { settingsViewModel.setDarkMode(it) }
                    )
                }
            }
            
            // Theme color selection
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Warna Tema",
                        style = MaterialTheme.typography.titleMedium
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        ColorOption(
                            color = Green,
                            isSelected = themeColor == 0,
                            onSelect = { settingsViewModel.setThemeColor(0) }
                        )
                        ColorOption(
                            color = Blue,
                            isSelected = themeColor == 1,
                            onSelect = { settingsViewModel.setThemeColor(1) }
                        )
                        ColorOption(
                            color = Purple,
                            isSelected = themeColor == 2,
                            onSelect = { settingsViewModel.setThemeColor(2) }
                        )
                        ColorOption(
                            color = Orange,
                            isSelected = themeColor == 3,
                            onSelect = { settingsViewModel.setThemeColor(3) }
                        )
                        ColorOption(
                            color = Red,
                            isSelected = themeColor == 4,
                            onSelect = { settingsViewModel.setThemeColor(4) }
                        )
                    }
                }
            }
            
            // View type selection
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Tampilan Daftar",
                        style = MaterialTheme.typography.titleMedium
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        ViewTypeOption(
                            title = "List",
                            isSelected = listViewType == "list",
                            onSelect = {
                                settingsViewModel.setListViewType("list")
                            }
                        )
                        ViewTypeOption(
                            title = "Grid",
                            isSelected = listViewType == "grid",
                            onSelect = {
                                settingsViewModel.setListViewType("grid")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ColorOption(
    color: Color,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .clip(CircleShape)
            .background(color)
            .clickable { onSelect() }
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Selected",
                tint = if (color == Orange) Color.Black else Color.White
            )
        }
    }
}

@Composable
fun ViewTypeOption(
    title: String,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Card(
        modifier = Modifier
            .clickable { onSelect() }
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
            )
            
            if (isSelected) {
                Spacer(modifier = Modifier.size(8.dp))
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
} 