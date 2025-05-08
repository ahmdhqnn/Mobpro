package org.ahmad0122.mobpro1.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.ahmad0122.mobpro1.ui.viewmodel.MainViewModel
import org.ahmad0122.mobpro1.ui.components.TransaksiList
import org.ahmad0122.mobpro1.ui.components.TransaksiGrid
import org.ahmad0122.mobpro1.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToAdd: () -> Unit,
    onNavigateToEdit: (Long) -> Unit,
    onNavigateToRecycleBin: () -> Unit,
    viewModel: MainViewModel = viewModel()
) {
    val transaksi by viewModel.transaksi.collectAsState(initial = emptyList())
    val totalPendapatan by viewModel.totalPendapatan.collectAsState(initial = 0.0)
    val totalPengeluaran by viewModel.totalPengeluaran.collectAsState(initial = 0.0)
    val isGridView by viewModel.isGridView.collectAsState()
    val isDarkMode by viewModel.isDarkMode.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Catatan Keuangan") },
                actions = {
                    IconButton(onClick = { viewModel.setGridView(!isGridView) }) {
                        Icon(
                            painter = painterResource(
                                if (isGridView) R.drawable.baseline_view_list_24 else R.drawable.baseline_grid_view_24
                            ),
                            contentDescription = if (isGridView) "Tampilan List" else "Tampilan Grid"
                        )
                    }
                    IconButton(onClick = { viewModel.setDarkMode(!isDarkMode) }) {
                        Icon(
                            painter = painterResource(
                                if (isDarkMode) R.drawable.baseline_light_mode_24 else R.drawable.baseline_dark_mode_24
                            ),
                            contentDescription = if (isDarkMode) "Mode Terang" else "Mode Gelap"
                        )
                    }
                    IconButton(onClick = onNavigateToRecycleBin) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Recycle Bin"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToAdd) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah Transaksi"
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Total Pendapatan",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Rp ${totalPendapatan.toInt()}",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = "Total Pengeluaran",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Rp ${totalPengeluaran.toInt()}",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = "Saldo",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Rp ${(totalPendapatan - totalPengeluaran).toInt()}",
                        style = MaterialTheme.typography.headlineMedium,
                        color = if (totalPendapatan >= totalPengeluaran) 
                            MaterialTheme.colorScheme.primary 
                        else 
                            MaterialTheme.colorScheme.error
                    )
                }
            }
            
            if (transaksi.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Belum ada transaksi",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                if (isGridView) {
                    TransaksiGrid(
                        transaksiList = transaksi,
                        onItemClick = { transaksi ->
                            onNavigateToEdit(transaksi.id)
                        },
                        onDeleteClick = { transaksi ->
                            viewModel.softDeleteTransaksi(transaksi.id)
                        }
                    )
                } else {
                    TransaksiList(
                        transaksiList = transaksi,
                        onItemClick = { transaksi ->
                            onNavigateToEdit(transaksi.id)
                        },
                        onDeleteClick = { transaksi ->
                            viewModel.softDeleteTransaksi(transaksi.id)
                        }
                    )
                }
            }
        }
    }
} 