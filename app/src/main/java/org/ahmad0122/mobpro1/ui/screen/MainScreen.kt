package org.ahmad0122.mobpro1.ui.screen

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.ahmad0122.mobpro1.R
import org.ahmad0122.mobpro1.ui.theme.Mobpro1Theme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    Scaffold (
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
        }
    ) {
            innerPadding ->
        ScreenContent(Modifier.padding((innerPadding)))
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun ScreenContent(modifier: Modifier = Modifier) {
    var panjang by remember { mutableStateOf("") }
    var lebar by remember { mutableStateOf("") }
    var hasilLuas by remember { mutableStateOf("") }
    var hasilKeliling by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    Column (
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(R.string.app_intro),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = panjang,
            onValueChange = { panjang = it },
            label = {Text(text = stringResource(R.string.panjang))},
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth(),
            isError = isError && panjang.isEmpty()
        )
        OutlinedTextField(
            value = lebar,
            onValueChange = { lebar = it },
            label = {Text(text = stringResource(R.string.lebar))},
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth(),
            isError = isError && lebar.isEmpty()
        )
        Spacer(modifier = Modifier.height(18.dp))

        Button(
            onClick = {
                if (panjang.isEmpty() || lebar.isEmpty()) {
                    isError = true
                    hasilLuas = ""
                    hasilKeliling = ""
                } else {
                    isError = false
                    val panjangV = panjang.toFloat()
                    val lebarV = lebar.toFloat()

                    val luas = panjangV * lebarV
                    val keliling = 2 * (panjangV + lebarV)

                    hasilLuas = String.format("%2f", luas)
                    hasilKeliling = String.format("%2f", keliling)

                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.hitung))
        }
        Spacer(modifier = Modifier.height(12.dp))

        if (hasilLuas.isNotEmpty() && hasilKeliling.isNotEmpty()) {
            Text(text = "Luas: $hasilLuas", fontSize = 18.sp)
            Text(text = "Keliling $hasilKeliling", fontSize = 18.sp)
        }

        Button(
            onClick = {
                panjang = ""
                lebar = ""
                hasilLuas = ""
                hasilKeliling = ""
                isError = false
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.reset))
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview() {
    Mobpro1Theme {
        MainScreen()
    }
}