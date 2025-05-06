package org.ahmad0122.mobpro1.ui.screen

import android.content.res.Configuration
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.ahmad0122.mobpro1.ui.theme.Mobpro1Theme

@Composable
fun DisplayAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
) {
    AlertDialog(
        title = { Text(text = "Konfirmasi Hapus") },
        text = { Text(text = "Apakah Anda yakin ingin menghapus transaksi ini? Transaksi akan dipindahkan ke Recycle Bin dan dapat dipulihkan nanti.") },
        confirmButton = {
            TextButton(onClick = { onConfirmation() }) {
                Text(text = "Hapus")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest() }) {
                Text(text = "Batal")
            }
        },
        onDismissRequest = { onDismissRequest() }
    )
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DialogPreview() {
    Mobpro1Theme {
        DisplayAlertDialog(
            onDismissRequest = {},
            onConfirmation = {}
        )
    }
}