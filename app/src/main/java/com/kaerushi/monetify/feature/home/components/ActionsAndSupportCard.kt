package com.kaerushi.monetify.feature.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.kaerushi.monetify.R

@Composable
fun ActionsAndSupportCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ImportExportRow(
                onImport = {

                },
                onExport = {

                }
            )
            SupportCard()
        }
    }
}

@Composable
private fun ImportExportRow(onImport: () -> Unit, onExport: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedButton(modifier = Modifier.weight(1f), onClick = onImport) {
            Text("Import")
        }
        Button(modifier = Modifier.weight(1f), onClick = onExport) {
            Text("Export")
        }
    }
}

@Composable
private fun SupportCard() {
    val uriHandler = LocalUriHandler.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(
                    "Support",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 6.dp),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    "If you encounter any issues or have feedback, please visit telegram or github",
                    fontSize = 14.sp,
                    style = TextStyle(lineHeight = 1.5.em),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            IconButton(onClick = {
                uriHandler.openUri("https://t.me/weeabooify")
            }) {
                Icon(
                    modifier = Modifier
                        .size(42.dp)
                        .padding(6.dp),
                    painter = painterResource(R.drawable.telegram),
                    contentDescription = "Telegram",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
            IconButton(onClick = {
                uriHandler.openUri("https://github.com/kaerushi")
            }) {
                Icon(
                    modifier = Modifier
                        .size(42.dp)
                        .padding(6.dp),
                    painter = painterResource(R.drawable.github),
                    contentDescription = "GitHub",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}