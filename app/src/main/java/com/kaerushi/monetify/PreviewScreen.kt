package com.kaerushi.monetify

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.kaerushi.monetify.core.ui.theme.MonetifyTheme

@Composable
fun PreviewScreen(content: @Composable () -> Unit) {
    MonetifyTheme {
        Surface {
            content()
        }
    }
}