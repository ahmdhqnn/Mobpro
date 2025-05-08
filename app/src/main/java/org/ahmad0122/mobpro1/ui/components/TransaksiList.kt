package org.ahmad0122.mobpro1.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.ahmad0122.mobpro1.model.Transaksi
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TransaksiList(
    transaksiList: List<Transaksi>,
    onItemClick: (Transaksi) -> Unit,
    onDeleteClick: (Transaksi) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        items(transaksiList) { transaksi ->
            TransaksiListItem(
                transaksi = transaksi,
                onClick = { onItemClick(transaksi) },
                onDeleteClick = { onDeleteClick(transaksi) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransaksiListItem(
    transaksi: Transaksi,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = transaksi.keterangan,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        .format(transaksi.tanggal),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
                        .format(transaksi.jumlah),
                    style = MaterialTheme.typography.titleMedium,
                    color = if (transaksi.jenis == "PENDAPATAN") 
                        MaterialTheme.colorScheme.primary 
                    else 
                        MaterialTheme.colorScheme.error
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Row {
                    if (transaksi.isDeleted) {
                        IconButton(onClick = onClick) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Restore"
                            )
                        }
                    }
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Hapus"
                        )
                    }
                }
            }
        }
    }
    
    if (showDeleteDialog) {
        DeleteConfirmationDialog(
            onDismiss = { showDeleteDialog = false },
            onConfirm = {
                onDeleteClick()
                showDeleteDialog = false
            }
        )
    }
} 