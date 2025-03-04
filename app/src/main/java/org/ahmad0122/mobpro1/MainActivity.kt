package org.ahmad0122.mobpro1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.ahmad0122.mobpro1.model.Hewan
import org.ahmad0122.mobpro1.ui.theme.Mobpro1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Mobpro1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val data = listOf(
        Hewan("Ayam", R.drawable.ayam),
        Hewan("Bebek", R.drawable.bebek),
        Hewan("Domba", R.drawable.domba),
        Hewan("Kambing", R.drawable.kambing),
        Hewan("Sapi", R.drawable.sapi),
    )
    Scaffold {
        innerPadding ->
        ScreenContent(data[0], Modifier.padding(innerPadding))
    }
}

@Composable
fun ScreenContent(hewan: Hewan, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = hewan.imageResId),
            contentDescription = stringResource(R.string.gambar, hewan.nama),
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(132.dp)
        )
        Text(
            text = hewan.nama,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}
