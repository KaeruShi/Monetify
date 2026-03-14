package com.kaerushi.monetify.core.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppChip(name: String, color: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.35f)) {
    Card(
        colors = CardDefaults.cardColors(containerColor = color),
        shape = RoundedCornerShape(24.dp)
    ) {
        Text(
            name,
            fontSize = 11.sp,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 0.dp),
            color = Color.White
        )
    }
}