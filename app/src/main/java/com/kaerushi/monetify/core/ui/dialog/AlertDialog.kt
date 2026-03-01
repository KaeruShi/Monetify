package com.kaerushi.monetify.core.ui.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AlertDialog(
    title: String,
    desc: String = "",
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    confirmText: String = "Yes",
    dismissText: String = "Cancel"
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(24.dp), color =
                MaterialTheme.colorScheme.surfaceContainer
        ) {
            Column {
                // Title
                Text(
                    text = title,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 0.dp)
                )

                // Description
                Text(
                    text = desc, fontSize = 14.sp,
                    modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 16.dp, bottom = 16.dp)
                )

                // Action Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 20.dp, bottom = 18.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(onClick = { onDismiss() }, shapes = ButtonDefaults.shapes(), colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )) {
                        Text(dismissText)
                    }
                    Button(onClick = {
                        onConfirm()
                    }, shapes = ButtonDefaults.shapes(), modifier = Modifier.padding(start = 12.dp)) {
                        Text(text = confirmText)
                    }
                }
            }
        }
    }
}