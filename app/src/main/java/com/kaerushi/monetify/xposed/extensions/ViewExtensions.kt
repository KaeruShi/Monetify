package com.kaerushi.monetify.xposed.extensions

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.ViewTreeObserver

fun applyToView(view: View, color: Int) {
    view.setBackgroundColor(color)
}

fun applyToViewWithRadius(
    view: View,
    color: Int,
    topLeft: Float = 0f,
    topRight: Float = 0f,
    bottomRight: Float = 0f,
    bottomLeft: Float = 0f,
    radius: Float = 0f,
) {
    view.background = GradientDrawable().apply {
        setColor(color)

        if (radius > 0f) {
            cornerRadius = radius
        } else {
            cornerRadii = floatArrayOf(
                topLeft, topLeft,
                topRight, topRight,
                bottomRight, bottomRight,
                bottomLeft, bottomLeft
            )
        }
    }
}

fun setViewBackgroundColor(
    view: View, color: Int,
    topLeft: Float = 0f,
    topRight: Float = 0f,
    bottomRight: Float = 0f,
    bottomLeft: Float = 0f,
    radius: Float = 0f
) {
    fun apply() {
        if (radius > 0f || topLeft > 0f || topRight > 0f || bottomRight > 0f || bottomLeft > 0f)
            applyToViewWithRadius(view, color, topLeft, topRight, bottomRight, bottomLeft, radius)
        else applyToView(view, color)
    }

    apply()

    val listener = ViewTreeObserver.OnGlobalLayoutListener {
        apply()
    }

    view.viewTreeObserver.addOnGlobalLayoutListener(listener)

    view.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
        override fun onViewAttachedToWindow(v: View) {}
        override fun onViewDetachedFromWindow(v: View) {
            v.viewTreeObserver.removeOnGlobalLayoutListener(listener)
            v.removeOnAttachStateChangeListener(this)
        }
    })
}

fun View.getParent(level: Int): View? {
    var p: View? = this
    repeat(level) {
        p = p?.parent as? View
    }
    return p
}

fun floatToDp(context: Context, value: Float): Float = value * context.resources.displayMetrics.density