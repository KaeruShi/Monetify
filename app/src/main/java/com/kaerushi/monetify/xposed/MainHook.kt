package com.kaerushi.monetify.xposed

import com.highcapable.yukihookapi.annotation.xposed.InjectYukiHookWithXposed
import com.highcapable.yukihookapi.hook.factory.encase
import com.highcapable.yukihookapi.hook.log.YLog
import com.highcapable.yukihookapi.hook.xposed.proxy.IYukiHookXposedInit
import com.kaerushi.monetify.xposed.hooks.pinterest.PinterestHooks
import com.kaerushi.monetify.xposed.hooks.reddit.RedditHooks
import com.kaerushi.monetify.xposed.hooks.subslite.SubstratumLiteHooks
import com.kaerushi.monetify.xposed.hooks.twitter.TwitterHooks
import com.kaerushi.monetify.xposed.hooks.youtube.YoutubeHooks

@InjectYukiHookWithXposed(isUsingResourcesHook = true)
object MainHook : IYukiHookXposedInit {
    @Volatile var dexKitLoaded: Boolean = false
        private set

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
//        YoutubeHooks
    )
}