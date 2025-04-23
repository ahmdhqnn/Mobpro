package org.ahmad0122.mobpro1.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import org.ahmad0122.mobpro1.MahasiswaViewModel
import org.ahmad0122.mobpro1.R
import org.ahmad0122.mobpro1.model.Mahasiswa
import org.ahmad0122.mobpro1.navigation.MahasiswaScreen
import org.ahmad0122.mobpro1.util.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MahasiswaScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.app_name))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(MahasiswaScreen.FormTambah.route) }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(R.string.tambah_mahasiswa),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    ) { innerPadding ->
        MahasiswaContent(modifier = Modifier.padding(innerPadding), navController = navController)
    }
}

@Composable
fun MahasiswaContent(modifier: Modifier = Modifier, navController: NavHostController) {
    val context = LocalContext.current
    val factory = ViewModelFactory(context)
    val viewModel: MahasiswaViewModel = viewModel(factory = factory)
    val data by viewModel.data.collectAsState()

    if (data.isEmpty()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(R.string.list_kosong))
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 84.dp)
        ) {
            items(data) { mahasiswa ->
                MahasiswaItem(mahasiswa = mahasiswa) {
                    navController.navigate(MahasiswaScreen.FormUbah.withId(mahasiswa.id))
                }
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun MahasiswaItem(mahasiswa: Mahasiswa, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = mahasiswa.nama,
            fontWeight = FontWeight.Bold
        )
        Text(text = mahasiswa.nim)
        Text(text = mahasiswa.kelas)
    }
} 