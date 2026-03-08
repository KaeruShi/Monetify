package com.kaerushi.monetify.xposed

import android.content.res.XModuleResources
import android.content.res.XResources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import com.highcapable.yukihookapi.annotation.xposed.InjectYukiHookWithXposed
import com.highcapable.yukihookapi.hook.factory.encase
import com.highcapable.yukihookapi.hook.log.YLog
import com.highcapable.yukihookapi.hook.xposed.bridge.event.YukiXposedEvent
import com.highcapable.yukihookapi.hook.xposed.proxy.IYukiHookXposedInit
import com.kaerushi.monetify.data.FILES_PACKAGE_NAME
import com.kaerushi.monetify.xposed.hooks.files.FilesHooks
import com.kaerushi.monetify.xposed.hooks.pinterest.PinterestHooks
import com.kaerushi.monetify.xposed.hooks.reddit.RedditHooks
import com.kaerushi.monetify.xposed.hooks.subslite.SubstratumLiteHooks
import com.kaerushi.monetify.xposed.hooks.twitter.TwitterHooks
import androidx.core.graphics.drawable.toDrawable

@InjectYukiHookWithXposed(isUsingResourcesHook = true)
object MainHook : IYukiHookXposedInit {
    @Volatile var dexKitLoaded: Boolean = false
        private set

//    private lateinit var modRes: XModuleResources

//    override fun onXposedEvent() {
//        YukiXposedEvent.events {
//            onInitZygote {
//                modRes = XModuleResources.createInstance(it.modulePath, null)
//            }
//            onHandleInitPackageResources {
//
//            }
//        }
//    }

    override fun onHook() = encase {
        dexKitLoaded = runCatching {
            System.loadLibrary("dexkit")
            true
        }.onFailure {
            YLog.error("DexKit native load failed: $it")
        }.getOrDefault(false)

        if (dexKitLoaded) {
            YLog.debug("DexKit loaded")
        }

        HookRegistry.hooks.forEach { loadHooker(it) }
    }
}

private object HookRegistry {
    val hooks = listOf(
        PinterestHooks,
        RedditHooks,
        SubstratumLiteHooks,
        TwitterHooks,
        FilesHooks
    )
}