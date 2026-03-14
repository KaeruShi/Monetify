package com.kaerushi.monetify.xposed

import com.highcapable.yukihookapi.annotation.xposed.InjectYukiHookWithXposed
import com.highcapable.yukihookapi.hook.factory.encase
import com.highcapable.yukihookapi.hook.log.YLog
import com.highcapable.yukihookapi.hook.xposed.proxy.IYukiHookXposedInit
import com.kaerushi.monetify.xposed.hooks.files.FilesHooks
import com.kaerushi.monetify.xposed.hooks.github.GitHubHooks
import com.kaerushi.monetify.xposed.hooks.pinterest.PinterestHooks
import com.kaerushi.monetify.xposed.hooks.reddit.RedditHooks
import com.kaerushi.monetify.xposed.hooks.subslite.SubstratumLiteHooks
import com.kaerushi.monetify.xposed.hooks.twitter.TwitterHooks
import com.kaerushi.monetify.xposed.hooks.youtube.YoutubeHooks
import org.luckypray.dexkit.DexKitBridge

@InjectYukiHookWithXposed(isUsingResourcesHook = true)
object MainHook : IYukiHookXposedInit {
    @Volatile var dexKitLoaded: Boolean = false
        private set

    lateinit var bridge: DexKitBridge

    override fun onHook() = encase {
        dexKitLoaded = runCatching {
            System.loadLibrary("dexkit")
            true
        }.onFailure {
            YLog.error("DexKit native load failed: $it")
        }.getOrDefault(false)

        HookRegistry.hooks.forEach { loadHooker(it) }
    }
}

private object HookRegistry {
    val hooks = listOf(
        PinterestHooks,
        RedditHooks,
        SubstratumLiteHooks,
        TwitterHooks,
        FilesHooks,
        GitHubHooks,
        YoutubeHooks
    )
}