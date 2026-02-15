package com.kaerushi.monetify.xposed.hooks

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.highcapable.kavaref.KavaRef.Companion.resolve
import com.kaerushi.monetify.R
import com.kaerushi.monetify.data.SUBSTRATUM_LITE_PACKAGE_NAME
import androidx.core.graphics.drawable.toDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.highcapable.yukihookapi.hook.core.YukiMemberHookCreator
import com.highcapable.yukihookapi.hook.core.annotation.LegacyResourcesHook
import com.highcapable.yukihookapi.hook.factory.field
import androidx.core.graphics.toColorInt

object SubstratumLiteHooks : BaseAppHook() {

    private var layoutInflaterResult: YukiMemberHookCreator.MemberHookCreator.Result? = null

    override val pkgName: String = SUBSTRATUM_LITE_PACKAGE_NAME
    override val duotoneDrawables: Map<String, Int> = mapOf(
        "ic_appintro_arrow" to R.drawable.duotone_arrow_back,
        "ic_manager_info" to R.drawable.duotone_info,
        "nav_drawer_themes" to R.drawable.duoltone_starfilled,
        "nav_drawer_priorities" to R.drawable.duotone_pin,
        "nav_drawer_manager" to R.drawable.duotone_home
    )

    @SuppressLint("DiscouragedApi")
    @LegacyResourcesHook
    override fun hookClass() {
        "$pkgName.activity.MainActivity".toClass().resolve().firstMethod {
            name = "onCreate"
            parameters(Bundle::class.java)
        }.hook {
            after {
                val activity = instance as Activity
                val isDarkMode = (activity.resources.configuration.uiMode and
                        Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
                val colorResource = if (isDarkMode) {
                    android.R.color.system_neutral1_900  // Dark mode
                } else {
                    android.R.color.system_neutral1_50   // Light mode
                }
                val bottomBarId = activity.resources.getIdentifier("bottomBar", "id", pkgName)
                bottomBarId.let { id ->
                    val bottomBarView = activity.findViewById<View>(id)
                    bottomBarView?.setBackgroundColor(activity.resources.getColor(colorResource, activity.theme))
                }
            }
        }
    }
}