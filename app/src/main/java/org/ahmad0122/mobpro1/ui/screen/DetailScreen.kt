package org.ahmad0122.mobpro1.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Check
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.ahmad0122.mobpro1.MainViewModel
import org.ahmad0122.mobpro1.R
import org.ahmad0122.mobpro1.ui.theme.Mobpro1Theme

const val KEY_ID_MAHASISWA = "idMahasiswa"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, id: Long? = null) {
    val viewModel: MainViewModel = viewModel()
    var nama by remember { mutableStateOf(("")) }
    var nim by remember { mutableStateOf("") }
    var kelas by remember { mutableStateOf("D3IF-46-01") }

    LaunchedEffect(Unit) {
        if (id == null) return@LaunchedEffect
        val data = viewModel.getMahasiswa(id)?: return@LaunchedEffect
        nama = data.nama
        nim = data.nim
        kelas = data.kelas
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
                        Text(text = stringResource(id = R.string.tambah_mahasiswa))
                    else
                        Text(text = stringResource(id = R.string.ubah_mahasiswa))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(onClick = {navController.popBackStack()}) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(R.string.simpan),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) {
            padding ->
        FormMahasiswa(
            nama = nama,
            onNamaChange = { nama = it },
            nim = nim,
            onNimChange = { nim = it },
            kelas = kelas,
            onKelasChange = { kelas = it },
            modifier = Modifier.padding(padding)
        )
    }
}

@Composable
fun FormMahasiswa(
    nama: String, onNamaChange: (String) -> Unit,
    nim: String, onNimChange: (String) -> Unit,
    kelas: String, onKelasChange: (String) -> Unit,
    modifier: Modifier
) {
    Column (
        modifier = modifier.fillMaxWidth().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Nama field
        OutlinedTextField(
            value = nama,
            onValueChange = { onNamaChange(it) },
            label = { Text(text = stringResource(R.string.nama)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        
        // NIM field
        OutlinedTextField(
            value = nim,
            onValueChange = { onNimChange(it) },
            label = { Text(text = stringResource(R.string.nim)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        
        // Kelas radio buttons
        Text(text = stringResource(R.string.kelas))
        
        KelasRadioButton(
            selected = kelas == "D3IF-46-01",
            onSelected = { onKelasChange("D3IF-46-01") },
            label = "D3IF-46-01"
        )
        
        KelasRadioButton(
            selected = kelas == "D3IF-46-02",
            onSelected = { onKelasChange("D3IF-46-02") },
            label = "D3IF-46-02"
        )
        
        KelasRadioButton(
            selected = kelas == "D3IF-46-03",
            onSelected = { onKelasChange("D3IF-46-03") },
            label = "D3IF-46-03"
        )
        
        KelasRadioButton(
            selected = kelas == "D3IF-46-04",
            onSelected = { onKelasChange("D3IF-46-04") },
            label = "D3IF-46-04"
        )
        
        KelasRadioButton(
            selected = kelas == "D3IF-46-05",
            onSelected = { onKelasChange("D3IF-46-05") },
            label = "D3IF-46-05"
        )
    }
}

@Composable
fun KelasRadioButton(
    selected: Boolean,
    onSelected: () -> Unit,
    label: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        RadioButton(
            selected = selected,
            onClick = onSelected
        )
        Text(text = label)
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