package org.ahmad0122.mobpro1.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import org.ahmad0122.mobpro1.R
import org.ahmad0122.mobpro1.model.Transaksi
import org.ahmad0122.mobpro1.ui.components.TransaksiGrid
import org.ahmad0122.mobpro1.ui.components.TransaksiList
import org.ahmad0122.mobpro1.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecycleBinScreen(
    onNavigateBack: () -> Unit,
    viewModel: MainViewModel = viewModel()
) {
    val transaksiTerhapus by viewModel.transaksiTerhapus.collectAsState(initial = emptyList())
    val isGridView by viewModel.isGridView.collectAsState()
    var showRestoreDialog by remember { mutableStateOf(false) }
    var selectedTransaksi by remember { mutableStateOf<Transaksi?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recycle Bin") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.setGridView(!isGridView) }) {
                        Icon(
                            painter = painterResource(
                                if (isGridView) R.drawable.baseline_view_list_24 else R.drawable.baseline_grid_view_24
                            ),
                            contentDescription = if (isGridView) "Tampilan List" else "Tampilan Grid"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        if (transaksiTerhapus.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tidak ada item yang dihapus",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            if (isGridView) {
                TransaksiGrid(
                    transaksiList = transaksiTerhapus,
                    onItemClick = { transaksi ->
                        selectedTransaksi = transaksi
                        showRestoreDialog = true
                    },
                    onDeleteClick = { transaksi ->
                        viewModel.deletePermanently(transaksi.id)
                    },
                    modifier = Modifier.padding(paddingValues)
                )
            } else {
                TransaksiList(
                    transaksiList = transaksiTerhapus,
                    onItemClick = { transaksi ->
                        selectedTransaksi = transaksi
                        showRestoreDialog = true
                    },
                    onDeleteClick = { transaksi ->
                        viewModel.deletePermanently(transaksi.id)
                    },
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
    
    if (showRestoreDialog && selectedTransaksi != null) {
        AlertDialog(
            onDismissRequest = { 
                showRestoreDialog = false
                selectedTransaksi = null
            },
            title = { Text("Restore Transaksi") },
            text = { Text("Apakah Anda yakin ingin mengembalikan transaksi ini?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        selectedTransaksi?.let {
                            viewModel.restoreTransaksi(it.id)
                        }
                        showRestoreDialog = false
                        selectedTransaksi = null
                    }
                ) {
                    Text("Ya")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showRestoreDialog = false
                        selectedTransaksi = null
                    }
                ) {
                    Text("Tidak")
                }
            }
        )
    }
} 