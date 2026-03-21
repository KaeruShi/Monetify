package com.kaerushi.monetify.core.utils

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.ScrollState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.highcapable.yukihookapi.YukiHookAPI
import com.topjohnwu.superuser.Shell
import kotlin.math.min

object Utils {
    val isModuleActive = YukiHookAPI.Status.isXposedModuleActive
    fun Context.launchApp(pkgName: String, kill: Boolean) {
        if (kill) Shell.cmd("am force-stop $pkgName").exec()
        try {
            val launchIntent = this.packageManager.getLaunchIntentForPackage(pkgName)
            if (launchIntent != null) {
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(launchIntent)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun Modifier.fadingEdges(
        scrollState: ScrollState,
        topEdgeHeight: Dp = 72.dp,
        bottomEdgeHeight: Dp = 72.dp
    ): Modifier = this.then(
        Modifier
            // adding layer fixes issue with blending gradient and content
            .graphicsLayer { alpha = 0.99F }
            .drawWithContent {
                drawContent()

                val topColors = listOf(Color.Transparent, Color.Black)
                val topStartY = scrollState.value.toFloat()
                val topGradientHeight = min(topEdgeHeight.toPx(), topStartY)
                drawRect(
                    brush = Brush.verticalGradient(
                        colors = topColors,
                        startY = topStartY,
                        endY = topStartY + topGradientHeight
                    ),
                    blendMode = BlendMode.DstIn
                )

                val bottomColors = listOf(Color.Black, Color.Transparent)
                val bottomEndY = size.height - scrollState.maxValue + scrollState.value
                val bottomGradientHeight = min(bottomEdgeHeight.toPx(), scrollState.maxValue.toFloat() - scrollState.value)
                if (bottomGradientHeight != 0f) drawRect(
                    brush = Brush.verticalGradient(
                        colors = bottomColors,
                        startY = bottomEndY - bottomGradientHeight,
                        endY = bottomEndY
                    ),
                    blendMode = BlendMode.DstIn
                )
            }
    )
}