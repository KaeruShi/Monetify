package com.kaerushi.monetify.feature.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaerushi.monetify.PreviewScreen

@Composable
fun ModuleStatusCard(title: String, subtitle: String, isActive: Boolean) {
    val cardColor = if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.errorContainer
    val cardIcon = if (isActive) Icons.Default.CheckCircle else Icons.Default.Warning
    val cardItemColor = if (isActive) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onErrorContainer

    Card(
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = cardIcon,
                contentDescription = "Card Icon",
                tint = cardItemColor
            )
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(text = title, color = cardItemColor, fontSize = 18.sp)
                Text(text = subtitle, color = cardItemColor)
            }
        }
    }
}