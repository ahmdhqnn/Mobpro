package org.ahmad0122.mobpro1.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import org.ahmad0122.mobpro1.R
import org.ahmad0122.mobpro1.navigation.KEY_ID_MAHASISWA
import org.ahmad0122.mobpro1.util.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MahasiswaDetailScreen(navController: NavHostController, id: Long? = null) {
    val context = LocalContext.current
    val factory = ViewModelFactory(context)
    val viewModel: MahasiswaDetailViewModel = viewModel(factory = factory)
    var nama by remember { mutableStateOf(("")) }
    var nim by remember { mutableStateOf("") }
    var kelas by remember { mutableStateOf("") }
    
    var checkedKelas by remember { mutableStateOf("") }
    
    // Daftar kelas yang tersedia
    val kelasList = listOf("D3IF-46-01", "D3IF-46-02", "D3IF-46-03", "D3IF-46-04", "D3IF-46-05")

    LaunchedEffect(Unit) {
        if (id == null) return@LaunchedEffect
        val data = viewModel.getMahasiswa(id)?: return@LaunchedEffect
        nama = data.nama
        nim = data.nim
        kelas = data.kelas
        checkedKelas = data.kelas
    }
    
    Scaffold (
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {navController.popBackStack()}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    if (id == null)
                        Text(text = stringResource(R.string.tambah_mahasiswa))
                    else
                        Text(text = stringResource(R.string.edit_mahasiswa))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(onClick = {
                        if (nama.isEmpty() || nim.isEmpty() || checkedKelas.isEmpty()) {
                            Toast.makeText(context, R.string.mahasiswa_invalid, Toast.LENGTH_LONG).show()
                            return@IconButton
                        }

                        if (id == null) {
                            viewModel.insert(nama, nim, checkedKelas)
                        } else {
                            viewModel.update(id, nama, nim, checkedKelas)
                        }
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(R.string.simpan),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    if (id != null) {
                        var showDeleteDialog by remember { mutableStateOf(false) }
                        
                        var expanded by remember { mutableStateOf(false) }
                        IconButton(onClick = { expanded = true }) {
                            Icon(
                                imageVector = Icons.Filled.MoreVert,
                                contentDescription = stringResource(R.string.lainnya),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text(stringResource(R.string.hapus_mahasiswa)) },
                                onClick = {
                                    expanded = false
                                    showDeleteDialog = true
                                }
                            )
                        }
                        
                        if (showDeleteDialog) {
                            AlertDialog(
                                onDismissRequest = { showDeleteDialog = false },
                                title = { Text(stringResource(R.string.konfirmasi_hapus)) },
                                text = { Text(stringResource(R.string.konfirmasi_hapus_mahasiswa)) },
                                confirmButton = {
                                    TextButton(
                                        onClick = {
                                            viewModel.delete(id)
                                            showDeleteDialog = false
                                            navController.popBackStack()
                                        }
                                    ) {
                                        Text(stringResource(R.string.hapus))
                                    }
                                },
                                dismissButton = {
                                    TextButton(
                                        onClick = { showDeleteDialog = false }
                                    ) {
                                        Text(stringResource(R.string.batal))
                                    }
                                }
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = nama,
                onValueChange = { nama = it },
                label = { Text(stringResource(R.string.nama)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth()
            )
            
            OutlinedTextField(
                value = nim,
                onValueChange = { nim = it },
                label = { Text(stringResource(R.string.nim)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth()
            )
            
            Text(stringResource(R.string.kelas) + ":")
            
            Column {
                kelasList.forEach { kelasOption ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        RadioButton(
                            selected = checkedKelas == kelasOption,
                            onClick = { checkedKelas = kelasOption }
                        )
                        Text(text = kelasOption)
                    }
                }
            }
        }
    }
} 