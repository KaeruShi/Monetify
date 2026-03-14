package com.kaerushi.monetify.xposed.helper

import android.content.Context
import android.view.View
import android.view.ViewGroup

class InjectLayoutHelper(
    val root: ViewGroup,
    val context: Context
) {
    fun findId(name: String): Int {
        return context.resources.getIdentifier(
            name,
            "id",
            context.packageName
        )
    }

    fun findView(name: String): View {
        return root.findViewById(findId(name))
    }
}