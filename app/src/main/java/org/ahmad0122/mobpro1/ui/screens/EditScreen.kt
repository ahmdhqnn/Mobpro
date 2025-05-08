package org.ahmad0122.mobpro1.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.ahmad0122.mobpro1.ui.viewmodel.MainViewModel
import org.ahmad0122.mobpro1.model.Transaksi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(
    onNavigateBack: () -> Unit,
    viewModel: MainViewModel = viewModel()
) {
    var keterangan by remember { mutableStateOf("") }
    var jumlah by remember { mutableStateOf("") }
    var jenis by remember { mutableStateOf("PENDAPATAN") }
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var transaksi by remember { mutableStateOf<Transaksi?>(null) }

    LaunchedEffect(Unit) {
        val transaksiId = viewModel.currentTransaksiId
        if (transaksiId != null) {
            transaksi = viewModel.getTransaksiById(transaksiId)
            transaksi?.let {
                keterangan = it.keterangan
                jumlah = it.jumlah.toString()
                jenis = it.jenis
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Transaksi") },
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
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = keterangan,
                onValueChange = { keterangan = it },
                label = { Text("Keterangan") },
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(
                value = jumlah,
                onValueChange = { jumlah = it },
                label = { Text("Jumlah") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { jenis = "PENDAPATAN" },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (jenis == "PENDAPATAN") 
                            MaterialTheme.colorScheme.primaryContainer 
                        else 
                            MaterialTheme.colorScheme.surface
                    )
                ) {
                    Text("Pendapatan")
                }
                
                OutlinedButton(
                    onClick = { jenis = "PENGELUARAN" },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (jenis == "PENGELUARAN") 
                            MaterialTheme.colorScheme.errorContainer 
                        else 
                            MaterialTheme.colorScheme.surface
                    )
                ) {
                    Text("Pengeluaran")
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Button(
                onClick = {
                    if (keterangan.isBlank()) {
                        showError = true
                        errorMessage = "Keterangan tidak boleh kosong"
                        return@Button
                    }
                    
                    val jumlahDouble = try {
                        jumlah.toDouble()
                    } catch (_: NumberFormatException) {
                        showError = true
                        errorMessage = "Jumlah harus berupa angka"
                        return@Button
                    }
                    
                    if (jumlahDouble <= 0) {
                        showError = true
                        errorMessage = "Jumlah harus lebih dari 0"
                        return@Button
                    }
                    
                    transaksi?.let {
                        viewModel.updateTransaksi(
                            it.copy(
                                keterangan = keterangan,
                                jumlah = jumlahDouble,
                                jenis = jenis
                            )
                        )
                    }
                    onNavigateBack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Simpan")
            }
        }
    }
    
    if (showError) {
        AlertDialog(
            onDismissRequest = { showError = false },
            title = { Text("Error") },
            text = { Text(errorMessage) },
            confirmButton = {
                TextButton(onClick = { showError = false }) {
                    Text("OK")
                }
            }
        )
    }
} 