package com.kaerushi.monetify.core.ui.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun TextInputDialog(
    title: String,
    label: String = "",
    initialText: String = "",
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit,
    confirmText: String? = null,
    dismissText: String = "Cancel"
) {
    var text by rememberSaveable { mutableStateOf(initialText) }
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(24.dp), color =
                MaterialTheme.colorScheme.surfaceContainer
        ) {
            Column {
                // Title
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 0.dp)
                )

                // Input Field
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    label = { Text(label) }
                )

                // Action Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 12.dp, bottom = 10.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    if (confirmText != null) {
                        TextButton(enabled = text.trim().isNotEmpty(), onClick = {
                            onConfirm(text.trim())
                        }) {
                            Text(text = confirmText)
                        }
                    }
                    TextButton(onClick = { onDismiss() }) { Text(dismissText) }
                }
            }
        }
    }
}