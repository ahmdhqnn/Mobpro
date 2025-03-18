package org.ahmad0122.mobpro1.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.ahmad0122.mobpro1.R
import org.ahmad0122.mobpro1.navigation.Screen
import org.ahmad0122.mobpro1.ui.theme.Mobpro1Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.app_name))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screen.About.route)
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = stringResource(R.string.tentang_aplikasi),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        ScreenContent(Modifier.padding(innerPadding))
    }
}

@Composable
fun ScreenContent(modifier: Modifier = Modifier) {
    var panjang by remember { mutableStateOf("") }
    var panjangError by remember { mutableStateOf(false) }

    var lebar by remember { mutableStateOf("") }
    var lebarError by remember { mutableStateOf(false) }

    var luas by remember { mutableFloatStateOf(0f) }
    var keliling by remember { mutableFloatStateOf(0f) }

    fun resetV() {
        panjang = ""
        lebar = ""
        panjangError = false
        lebarError = false
        luas = 0f
        keliling = 0f
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.app_bio),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = panjang,
            onValueChange = { panjang = it },
            label = { Text(text = stringResource(R.string.panjang)) },
            supportingText = { ErrorHint(panjangError) },
            isError = panjangError,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = lebar,
            onValueChange = { lebar = it },
            label = { Text(text = stringResource(R.string.lebar)) },
            supportingText = { ErrorHint(lebarError) },
            isError = lebarError,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    panjangError = false
                    lebarError = false

                    if (panjang.isBlank() || panjang.toFloatOrNull() == null || panjang.toFloat() <= 0) {
                        panjangError = true
                    }
                    if (lebar.isBlank() || lebar.toFloatOrNull() == null || lebar.toFloat() <= 0) {
                        lebarError = true
                    }

                    if (panjangError || lebarError) {
                        return@Button
                    }

                    val panjangV = panjang.toFloat()
                    val lebarV = lebar.toFloat()
                    luas = hitungLuas(panjangV, lebarV)
                    keliling = hitungKeliling(panjangV, lebarV)
                },
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
            ) {
                Text(text = stringResource(R.string.hitung))
            }

            Button(
                onClick = {
                    resetV()
                },
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
            ) {
                Text(text = stringResource(R.string.reset))
            }
        }

        if (luas > 0) {
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), thickness = 1.dp)
            Text(
                text = stringResource(R.string.luas_x, luas),
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = stringResource(R.string.keliling_x, keliling),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Composable
fun ErrorHint(isError: Boolean) {
    if (isError) {
        Text(text = stringResource(R.string.input_invalid), color = MaterialTheme.colorScheme.error)
    }
}

private fun hitungLuas(panjang: Float, lebar: Float): Float {
    return panjang * lebar
}

private fun hitungKeliling(panjang: Float, lebar: Float): Float {
    return (panjang + lebar) * 2
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview() {
    Mobpro1Theme {
        MainScreen(rememberNavController())
    }
}