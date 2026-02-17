package com.kaerushi.monetify.xposed.utils

import android.content.Context
import android.content.res.ColorStateList
import com.kaerushi.monetify.xposed.utils.Utils.isNightMode

object ColorUtils {
    // Primary colors
    fun colorPrimary(context: Context): Int =
        if (!isNightMode(context))
            context.getColor(android.R.color.system_accent1_600)
        else
            context.getColor(android.R.color.system_accent1_100)

    fun colorOnPrimary(context: Context): Int =
        if (!isNightMode(context))
            context.getColor(android.R.color.system_accent1_0)
        else
            context.getColor(android.R.color.system_accent1_1000)

    fun colorPrimaryContainer(context: Context): Int =
        if (!isNightMode(context))
            context.getColor(android.R.color.system_accent1_100)
        else
            context.getColor(android.R.color.system_accent1_100)

    fun colorOnPrimaryContainer(context: Context): Int =
        context.getColor(android.R.color.system_accent1_900)


    // Secondary colors
    fun colorSecondary(context: Context): Int =
        if (!isNightMode(context))
            context.getColor(android.R.color.system_accent2_600)
        else
            context.getColor(android.R.color.system_accent2_200)

    fun colorOnSecondary(context: Context): Int =
        if (!isNightMode(context))
            context.getColor(android.R.color.system_accent2_0)
        else
            context.getColor(android.R.color.system_accent2_1000)

    fun colorSecondaryContainer(context: Context): Int =
        if (!isNightMode(context))
            context.getColor(android.R.color.system_accent2_100)
        else
            context.getColor(android.R.color.system_accent2_700)

    fun colorOnSecondaryContainer(context: Context): Int =
        if (!isNightMode(context))
            context.getColor(android.R.color.system_accent2_900)
        else
            context.getColor(android.R.color.system_accent2_100)


    // Tertiary colors
    fun colorTertiary(context: Context): Int =
        if (!isNightMode(context))
            context.getColor(android.R.color.system_accent3_600)
        else
            context.getColor(android.R.color.system_accent3_200)

    fun colorOnTertiary(context: Context): Int =
        if (!isNightMode(context))
            context.getColor(android.R.color.system_accent3_0)
        else
            context.getColor(android.R.color.system_accent3_1000)

    fun colorTertiaryContainer(context: Context): Int =
        if (!isNightMode(context))
            context.getColor(android.R.color.system_accent3_100)
        else
            context.getColor(android.R.color.system_accent3_700)

    fun colorOnTertiaryContainer(context: Context): Int =
        if (!isNightMode(context))
            context.getColor(android.R.color.system_accent3_900)
        else
            context.getColor(android.R.color.system_accent3_100)


    // Error colors
    fun colorError(context: Context): Int {
        return if (!isNightMode(context)) context.resources.getColor(android.R.color.system_error_600, context.theme) else context.resources.getColor(android.R.color.system_error_200, context.theme)
    }

    fun colorOnError(context: Context): Int {
        return if (!isNightMode(context)) context.resources.getColor(android.R.color.system_error_0, context.theme) else context.resources.getColor(android.R.color.system_error_800, context.theme)
    }

    fun colorErrorContainer(context: Context): Int {
        return if (!isNightMode(context)) context.resources.getColor(android.R.color.system_error_100, context.theme) else context.resources.getColor(android.R.color.system_error_700, context.theme)
    }

    fun colorOnErrorContainer(context: Context): Int {
        return if (!isNightMode(context)) context.resources.getColor(android.R.color.system_error_700, context.theme) else context.resources.getColor(android.R.color.system_error_100, context.theme)
    }

    // Background colors
    fun colorSurfaceContainer(context: Context): Int =
        if (!isNightMode(context))
            context.getColor(android.R.color.system_neutral1_100)
        else
            context.getColor(android.R.color.system_neutral1_800)

    fun colorOnSurface(context: Context): Int =
        if (!isNightMode(context))
            context.getColor(android.R.color.system_neutral1_900)
        else
            context.getColor(android.R.color.system_neutral1_100)

    fun colorBackground(context: Context): Int =
        if (!isNightMode(context))
            context.getColor(android.R.color.system_neutral1_100)
        else
            context.getColor(android.R.color.system_neutral1_800)

    fun colorOnBackground(context: Context): Int =
        if (!isNightMode(context))
            context.getColor(android.R.color.system_neutral1_900)
        else
            context.getColor(android.R.color.system_neutral1_100)


    // Surface colors
    fun colorSurfaceVariant(context: Context): Int =
        if (!isNightMode(context))
            context.getColor(android.R.color.system_neutral2_200)
        else
            context.getColor(android.R.color.system_neutral2_700)

    fun colorOnSurfaceVariant(context: Context): Int =
        if (!isNightMode(context))
            context.getColor(android.R.color.system_neutral2_900)
        else
            context.getColor(android.R.color.system_neutral2_100)

    fun colorOutline(context: Context): Int =
        if (!isNightMode(context))
            context.getColor(android.R.color.system_neutral2_500)
        else
            context.getColor(android.R.color.system_neutral2_700)

    fun colorSurface(context: Context): Int =
        if (!isNightMode(context))
            context.getColor(android.R.color.system_neutral1_50)
        else
            context.getColor(android.R.color.system_neutral1_900)


    fun colorOutlineVariant(context: Context): Int {
        return if (!isNightMode(context)) context.resources.getColor(android.R.color.system_neutral2_300, context.theme) else context.resources.getColor(android.R.color.system_neutral2_700, context.theme)
    }

    // Scrim
    fun colorScrim(context: Context): Int {
        return context.resources.getColor(android.R.color.system_neutral1_1000, context.theme)
    }

    // Inverse colors
    fun colorInverseSurface(context: Context): Int {
        return if (!isNightMode(context)) context.resources.getColor(android.R.color.system_neutral1_800, context.theme) else context.resources.getColor(android.R.color.system_neutral1_100, context.theme)
    }

    fun colorInverseOnSurface(context: Context): Int {
        return if (!isNightMode(context)) context.resources.getColor(android.R.color.system_neutral1_50, context.theme) else context.resources.getColor(android.R.color.system_neutral1_800, context.theme)
    }

    fun colorInversePrimary(context: Context): Int {
        return if (!isNightMode(context)) context.resources.getColor(android.R.color.system_accent1_200, context.theme) else context.resources.getColor(android.R.color.system_accent1_600, context.theme)
    }

    // Surface container colors
    fun colorSurfaceDim(context: Context): Int {
        return if (!isNightMode(context)) context.resources.getColor(android.R.color.system_neutral1_100, context.theme) else context.resources.getColor(android.R.color.system_neutral1_900, context.theme)
    }

    fun colorSurfaceBright(context: Context): Int {
        return if (!isNightMode(context)) context.resources.getColor(android.R.color.system_neutral1_10, context.theme) else context.resources.getColor(android.R.color.system_neutral1_800, context.theme)
    }

    fun colorSurfaceContainerLowest(context: Context): Int {
        return if (!isNightMode(context)) context.resources.getColor(android.R.color.system_neutral1_0, context.theme) else context.resources.getColor(android.R.color.system_neutral1_1000, context.theme)
    }

    fun colorSurfaceContainerLow(context: Context): Int {
        return if (!isNightMode(context)) context.resources.getColor(android.R.color.system_neutral1_50, context.theme) else context.resources.getColor(android.R.color.system_neutral1_900, context.theme)
    }

    fun colorSurfaceContainerHigh(context: Context): Int {
        return if (!isNightMode(context)) context.resources.getColor(android.R.color.system_neutral1_100, context.theme) else context.resources.getColor(android.R.color.system_neutral1_800, context.theme)
    }

    fun colorSurfaceContainerHighest(context: Context): Int {
        return if (!isNightMode(context)) context.resources.getColor(android.R.color.system_neutral1_100, context.theme) else context.resources.getColor(android.R.color.system_neutral1_800, context.theme)
    }

    // Tint colors
    fun getTintColor(context: Context): ColorStateList {
        val states = arrayOf(
            intArrayOf(android.R.attr.state_checked),  // checked state
            intArrayOf(-android.R.attr.state_checked)  // unchecked state
        )
        val colors = intArrayOf(
            colorPrimary(context),  // checked color
            colorOnPrimary(context)   // unchecked color
        )
        return ColorStateList(states, colors)
    }
}