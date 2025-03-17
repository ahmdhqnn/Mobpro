package org.ahmad0122.mobpro1.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.ahmad0122.mobpro1.R
import org.ahmad0122.mobpro1.ui.theme.Mobpro1Theme
import kotlin.math.pow

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

@Composable
fun ScreenContent(modifier: Modifier = Modifier) {
    var berat by remember { mutableStateOf("") }
    var beratError by remember { mutableStateOf(false) }

    var tinggi by remember { mutableStateOf("") }
    var tinggiError by remember { mutableStateOf(false) }

    val radioOptions = listOf(
        stringResource(id = R.string.pria),
        stringResource(id = R.string.wanita)
    )
    var gender by remember { mutableStateOf(radioOptions[0]) }

    var bmi by remember { mutableFloatStateOf(0f) }
    var kategori by remember { mutableIntStateOf(0) }

    Column (
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.bmi_intro),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = berat,
            onValueChange = { berat = it },
            label = { Text(text = stringResource(R.string.berat_badan)) },
            trailingIcon = { IconPicker(beratError, "Kg") },
            supportingText = { ErrorHint(beratError) },
            isError = beratError,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()

        )
        OutlinedTextField(
            value = tinggi,
            onValueChange = { tinggi = it },
            label = { Text(text = stringResource(R.string.tinggi_badan)) },
            trailingIcon = { IconPicker(tinggiError, "Cm") },
            supportingText = { ErrorHint(tinggiError) },
            isError = tinggiError,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Row (
            modifier = Modifier
                .padding(top = 8.dp)
                .border(1.dp, Color.Gray,
                    RoundedCornerShape(4.dp))
        ) {
            radioOptions.forEach { text ->
                GenderOption(
                    label = text,
                    isSelected = gender == text,
                    modifier = Modifier.selectable(
                        selected = gender == text,
                        onClick = { gender = text },
                        role = Role.RadioButton
                    )
                        .weight(1f).padding(16.dp)
                )

            }
        }
        Button(
            onClick = {
                beratError = (berat == "" || berat == "0")
                tinggiError = (tinggi == "" || tinggi == "0")

                if (
                    beratError || tinggiError
                )
                    return@Button

                bmi = hitungBmi(berat.toFloat(), tinggi.toFloat())
                kategori = getKategori(bmi, gender == radioOptions[0])
            },
            modifier = Modifier.padding(top = 8.dp),
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
        ) {
            Text(text = stringResource(R.string.hitung))
        }

        if (bmi != 0f) {
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 1.dp
            )
            Text(text = stringResource(R.string.bmi_x, bmi),
                style = MaterialTheme.typography.titleLarge
            )
            Text(text = stringResource(kategori).uppercase(),
                style = MaterialTheme.typography.headlineLarge
            )
        }
    }
}

@Composable
fun GenderOption(label: String, isSelected: Boolean, modifier: Modifier) {
    Row (
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = isSelected, onClick = null)
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

private fun hitungBmi(berat: Float, tinggi: Float): Float {
    return berat / (tinggi / 100).pow(2)
}

private fun getKategori(bmi: Float, isMale: Boolean): Int {
    return if (isMale) {
        when {
            bmi < 20.5 -> R.string.kurus
            bmi >= 27.5 -> R.string.gemuk
            else -> R.string.ideal
        }
    }else {
        when {
            bmi < 18.5 -> R.string.kurus
            bmi >= 25.5 -> R.string.gemuk
            else -> R.string.ideal
        }
    }
}

@Composable
fun IconPicker(isError: Boolean, unit: String) {
    if (isError) {
        Icon(imageVector = Icons.Filled.Warning, contentDescription = null)
    } else {
        Text(text = unit)
    }
}

@Composable
fun ErrorHint(isError: Boolean) {
    if (isError) {
        Text(text = stringResource(R.string.input_invalid))
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview() {
    Mobpro1Theme {
        AboutScreen()
    }
}