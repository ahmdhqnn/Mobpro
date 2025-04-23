package org.ahmad0122.mobpro1.ui.screen

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import org.ahmad0122.mobpro1.ui.theme.Mobpro1Theme
import org.ahmad0122.mobpro1.util.ViewModelFactory

const val KEY_ID_MAHASISWA = "idMahasiswa"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, id: Long? = null) {
    val context = LocalContext.current
    val factory = ViewModelFactory(context)
    val viewModel: DetailViewModel = viewModel(factory = factory)
    val scope = rememberCoroutineScope()
    
    var nim by remember { mutableStateOf("") }
    var nama by remember { mutableStateOf("") }
    var jurusan by remember { mutableStateOf("D3IF-46-01") }

    val jurusanOptions = listOf(
        "D3IF-46-01", 
        "D3IF-46-02", 
        "D3IF-46-03", 
        "D3IF-46-04", 
        "D3IF-46-05"
    )

    LaunchedEffect(key1 = id) {
        if (id != null) {
            val data = viewModel.getMahasiswa(id)
            if (data != null) {
                nim = data.nim
                nama = data.nama
                jurusan = data.jurusan
            }
        }
    }
    
    Scaffold (
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    if (id == null)
                        Text(text = "Tambah mahasiswa")
                    else
                        Text(text = "Ubah mahasiswa")
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(onClick = {
                        if (nim.isBlank() || nama.isBlank()) {
                            Toast.makeText(context, "Nama dan NIM harus diisi", Toast.LENGTH_LONG).show()
                            return@IconButton
                        }

                        scope.launch {
                            if (id == null) {
                                viewModel.insert(nim, nama, jurusan)
                            } else {
                                viewModel.update(id, nim, nama, jurusan)
                            }
                            navController.popBackStack()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = "Simpan",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    if (id != null) {
                        DeleteAction {
                            scope.launch {
                                viewModel.delete(id)
                                navController.popBackStack()
                            }
                        }
                    }
                }
            )
        }
    ) { padding ->
        FormMahasiswa(
            nim = nim,
            onNimChange = { nim = it },
            nama = nama,
            onNamaChange = { nama = it },
            jurusan = jurusan,
            jurusanOptions = jurusanOptions,
            onJurusanChange = { jurusan = it },
            isEdit = id != null,
            modifier = Modifier.padding(padding)
        )
    }
}

@Composable
fun DeleteAction(delete: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = "Menu lainnya",
            tint = MaterialTheme.colorScheme.primary
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = {
                    Text(text = "Hapus mahasiswa")
                },
                onClick = {
                    expanded = false
                    delete()
                }
            )
        }
    }
}

@Composable
fun FormMahasiswa(
    nim: String, 
    onNimChange: (String) -> Unit,
    nama: String, 
    onNamaChange: (String) -> Unit,
    jurusan: String, 
    jurusanOptions: List<String>,
    onJurusanChange: (String) -> Unit,
    isEdit: Boolean = false,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (isEdit) {
            // Label NIM di atas nama untuk mode edit
            Text(text = "NIM")
            Text(
                text = nama,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        
        // Field Nama
        OutlinedTextField(
            value = nama,
            onValueChange = onNamaChange,
            label = { Text(text = "Nama") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        
        // Field NIM
        OutlinedTextField(
            value = nim,
            onValueChange = onNimChange,
            label = { Text(text = "NIM") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        
        // Radio button group for jurusan
        Column {
            jurusanOptions.forEach { option ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (option == jurusan),
                            onClick = { onJurusanChange(option) },
                            role = Role.RadioButton
                        )
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (option == jurusan),
                        onClick = { onJurusanChange(option) }
                    )
                    Text(
                        text = option,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DetailScreenPreview() {
    Mobpro1Theme {
        DetailScreen(rememberNavController())
    }
}