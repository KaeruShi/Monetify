package com.kaerushi.monetify.xposed.utils

import android.content.Context
import android.content.res.ColorStateList
import com.kaerushi.monetify.xposed.extensions.isNightMode

// Primary colors
fun colorPrimary(context: Context): Int =
    if (!isNightMode(context))
        getSystemColor(context, android.R.color.system_accent1_600)
    else
        getSystemColor(context, android.R.color.system_accent1_200)

fun colorPrimaryLight(context: Context): Int = getSystemColor(context, android.R.color.system_accent1_600)
fun colorPrimaryDark(context: Context): Int = getSystemColor(context, android.R.color.system_accent1_200)

fun colorOnPrimary(context: Context): Int =
    if (!isNightMode(context))
        getSystemColor(context, android.R.color.system_accent1_0)
    else
        getSystemColor(context, android.R.color.system_accent1_1000)

fun colorPrimaryContainer(context: Context): Int =
    if (!isNightMode(context))
        getSystemColor(context, android.R.color.system_accent1_100)
    else
        getSystemColor(context, android.R.color.system_accent1_800)

fun colorOnPrimaryContainer(context: Context): Int =
    getSystemColor(context, android.R.color.system_accent1_900)


// Secondary colors
fun colorSecondary(context: Context): Int =
    if (!isNightMode(context))
        getSystemColor(context, android.R.color.system_accent2_600)
    else
        getSystemColor(context, android.R.color.system_accent2_200)

fun colorOnSecondary(context: Context): Int =
    if (!isNightMode(context))
        getSystemColor(context, android.R.color.system_accent2_0)
    else
        getSystemColor(context, android.R.color.system_accent2_1000)

fun colorSecondaryContainer(context: Context): Int =
    if (!isNightMode(context))
        getSystemColor(context, android.R.color.system_accent2_100)
    else
        getSystemColor(context, android.R.color.system_accent2_700)

fun colorOnSecondaryContainer(context: Context): Int =
    if (!isNightMode(context))
        getSystemColor(context, android.R.color.system_accent2_900)
    else
        getSystemColor(context, android.R.color.system_accent2_100)


// Tertiary colors
fun colorTertiary(context: Context): Int =
    if (!isNightMode(context))
        getSystemColor(context, android.R.color.system_accent3_600)
    else
        getSystemColor(context, android.R.color.system_accent3_200)

fun colorOnTertiary(context: Context): Int =
    if (!isNightMode(context))
        getSystemColor(context, android.R.color.system_accent3_0)
    else
        getSystemColor(context, android.R.color.system_accent3_1000)

fun colorTertiaryContainer(context: Context): Int =
    if (!isNightMode(context))
        getSystemColor(context, android.R.color.system_accent3_100)
    else
        getSystemColor(context, android.R.color.system_accent3_700)

fun colorOnTertiaryContainer(context: Context): Int =
    if (!isNightMode(context))
        getSystemColor(context, android.R.color.system_accent3_900)
    else
        getSystemColor(context, android.R.color.system_accent3_100)

fun colorOnSurface(context: Context): Int =
    if (!isNightMode(context))
        getSystemColor(context, android.R.color.system_neutral1_900)
    else
        getSystemColor(context, android.R.color.system_neutral1_100)

fun colorOnSurfaceLight(context: Context): Int = getSystemColor(context, android.R.color.system_neutral1_900)
fun colorOnSurfaceDark(context: Context): Int = getSystemColor(context, android.R.color.system_neutral1_100)

fun colorBackground(context: Context): Int =
    if (!isNightMode(context))
        getSystemColor(context, android.R.color.system_neutral1_0)
    else
        getSystemColor(context, android.R.color.system_neutral1_1000)

fun colorOnBackground(context: Context): Int =
    if (!isNightMode(context))
        getSystemColor(context, android.R.color.system_neutral1_900)
    else
        getSystemColor(context, android.R.color.system_neutral1_100)


// Surface colors
fun colorSurface(context: Context): Int =
    if (!isNightMode(context))
        getSystemColor(context, android.R.color.system_neutral1_50)
    else
        getSystemColor(context, android.R.color.system_neutral1_900)
fun colorSurfaceDim(context: Context): Int =
    if (!isNightMode(context))
        getSystemColor(context, android.R.color.system_neutral1_100)
    else
        getSystemColor(context, android.R.color.system_neutral1_900)

fun colorSurfaceBright(context: Context): Int =
    if (!isNightMode(context))
        getSystemColor(context, android.R.color.system_neutral1_10)
    else
        getSystemColor(context, android.R.color.system_neutral1_800)

fun colorSurfaceVariant(context: Context): Int =
    if (!isNightMode(context))
        getSystemColor(context, android.R.color.system_neutral2_200)
    else
        getSystemColor(context, android.R.color.system_neutral2_700)

fun colorOnSurfaceVariant(context: Context): Int =
    if (!isNightMode(context))
        getSystemColor(context, android.R.color.system_neutral2_900)
    else
        getSystemColor(context, android.R.color.system_neutral2_100)

fun colorOutline(context: Context): Int =
    if (!isNightMode(context))
        getSystemColor(context, android.R.color.system_neutral2_500)
    else
        getSystemColor(context, android.R.color.system_neutral2_700)

fun colorSurfaceLight(context: Context) = getSystemColor(context, android.R.color.system_neutral1_50)
fun colorSurfaceDark(context: Context) = getSystemColor(context, android.R.color.system_neutral1_900)

fun colorOutlineVariant(context: Context): Int =
    if (!isNightMode(context))
        getSystemColor(context, android.R.color.system_neutral2_300)
    else
        getSystemColor(context, android.R.color.system_neutral2_600)

// Scrim
fun colorScrim(context: Context): Int = getSystemColor(context, android.R.color.system_neutral1_1000)

// Inverse colors
fun colorInverseSurface(context: Context): Int =
    if (!isNightMode(context))
        getSystemColor(context, android.R.color.system_neutral1_800)
    else
        getSystemColor(context, android.R.color.system_neutral1_200)

fun colorInverseOnSurface(context: Context): Int =
    if (!isNightMode(context))
        getSystemColor(context, android.R.color.system_neutral1_50)
    else
        getSystemColor(context, android.R.color.system_neutral1_900)

fun colorInversePrimary(context: Context): Int =
    if (!isNightMode(context))
        getSystemColor(context, android.R.color.system_accent1_200)
    else
        getSystemColor(context, android.R.color.system_accent1_600)

// Surface container colors
fun colorSurfaceContainer(context: Context): Int =
    if (!isNightMode(context))
        getSystemColor(context, android.R.color.system_neutral1_100)
    else
        getSystemColor(context, android.R.color.system_neutral1_800)

fun colorSurfaceContainerLight(context: Context): Int = getSystemColor(context, android.R.color.system_neutral1_100)
fun colorSurfaceContainerDark(context: Context): Int = getSystemColor(context, android.R.color.system_neutral1_800)

fun colorSurfaceContainerLowest(context: Context): Int =
    if (!isNightMode(context))
        getSystemColor(context, android.R.color.system_neutral1_0)
    else
        getSystemColor(context, android.R.color.system_neutral1_1000)

fun colorSurfaceContainerLow(context: Context): Int =
    if (!isNightMode(context))
        getSystemColor(context, android.R.color.system_neutral1_50)
    else
        getSystemColor(context, android.R.color.system_neutral1_900)

fun colorSurfaceContainerHigh(context: Context): Int =
    if (!isNightMode(context))
        getSystemColor(context, android.R.color.system_neutral1_200)
    else
        getSystemColor(context, android.R.color.system_neutral1_800)

fun colorSurfaceContainerHighest(context: Context): Int =
    if (!isNightMode(context))
        getSystemColor(context, android.R.color.system_neutral1_300)
    else
        getSystemColor(context, android.R.color.system_neutral1_600)

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

fun getSystemColor(context: Context, color: Int) = context.resources.getColor(color, context.theme)