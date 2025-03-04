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
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.ahmad0122.mobpro1.ui.theme.Mobpro1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Mobpro1Theme {
                ChallengeLamp()
            }
        }
    }
}

@Composable
fun ChallengeLamp () {
    var lampIsOn by remember { mutableStateOf(false) }

    val imgRes = if (lampIsOn) R.drawable.lamp_on else R.drawable.lamp_off
    val statusText = if (lampIsOn) "Lampu Hidup" else "Lampu Mati"
    val  buttonText = if (lampIsOn) "Matikan" else "Hidupkan"

    Column (
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = imgRes),
            contentDescription = null
        )
        Text(
            text = statusText,
            modifier = Modifier.padding(top = 16.dp)
        )
        Button(
            onClick = { lampIsOn = !lampIsOn },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = buttonText)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChallengeLampPreview() {
    Mobpro1Theme {
        ChallengeLamp()
    }
}
