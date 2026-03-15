package com.kaerushi.monetify.core.ui.dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun <T> RadioSelectionDialog(
    title: String,
    options: List<T>,
    selected: T?,
    optionText: (T) -> String,
    onSelect: (T) -> Unit,
    onDismiss: () -> Unit,
    dismissText: String = "Cancel"
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(24.dp), color =
            MaterialTheme.colorScheme.surfaceContainer) {
            Column {
                // Title
                Text(
                    text = title,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 12.dp)
                )

                // Options List
                LazyColumn {
                    itemsIndexed(options) { _, option ->
                        val isSelected = option == selected
                        RadioOptionItem(
                            isSelected = isSelected,
                            onSelect = { onSelect(option) },
                            label = optionText(option)
                        )
                    }
                }

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
                }
            }
        }
    }
}

@Composable
private fun RadioOptionItem(
    isSelected: Boolean,
    onSelect: () -> Unit,
    label: String
) {
    Row(
        modifier = Modifier
            .clickable(role = Role.RadioButton) { onSelect() }
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = isSelected, onClick = onSelect)
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = label, modifier = Modifier.weight(1f))
    }
}