package com.kaerushi.monetify.xposed

import com.highcapable.yukihookapi.annotation.xposed.InjectYukiHookWithXposed
import com.highcapable.yukihookapi.hook.factory.configs
import com.highcapable.yukihookapi.hook.factory.encase
import com.highcapable.yukihookapi.hook.log.YLog
import com.highcapable.yukihookapi.hook.xposed.proxy.IYukiHookXposedInit
import com.kaerushi.monetify.BuildConfig
import com.kaerushi.monetify.xposed.hooks.android.FrameworkHook
import com.kaerushi.monetify.xposed.hooks.files.FilesHooks
import com.kaerushi.monetify.xposed.hooks.github.GitHubHooks
import com.kaerushi.monetify.xposed.hooks.instagram.InstagramHooks
import com.kaerushi.monetify.xposed.hooks.pinterest.PinterestHooks
import com.kaerushi.monetify.xposed.hooks.reddit.RedditHooks
import com.kaerushi.monetify.xposed.hooks.subslite.SubstratumLiteHooks
import com.kaerushi.monetify.xposed.hooks.twitter.TwitterHooks
import com.kaerushi.monetify.xposed.hooks.youtube.YoutubeHooks
import com.kaerushi.monetify.xposed.utils.DexKitFileCache
import org.luckypray.dexkit.DexKitBridge
import org.luckypray.dexkit.DexKitCacheBridge
import org.luckypray.dexkit.annotations.DexKitExperimentalApi
import java.util.concurrent.atomic.AtomicBoolean

@InjectYukiHookWithXposed(isUsingResourcesHook = true)
object MainHook : IYukiHookXposedInit {
    @Volatile var dexKitLoaded: Boolean = false
        private set
    private val dexKitCacheInitCalled = AtomicBoolean(false)

    fun createBridge(apkPath: String): DexKitBridge {
        return DexKitBridge.create(apkPath)
    }

    override fun onInit() = configs {
        debugLog {
            isRecord = true
            tag = "Monetify"
            elements(TAG, PRIORITY, PACKAGE_NAME)
        }
    }

    override fun onHook() = encase {
        if (appInfo.packageName != null && appInfo.packageName != "android"
            && appInfo.packageName != BuildConfig.APPLICATION_ID
        ) {
            dexKitLoaded = runCatching {
                System.loadLibrary("dexkit")
                true
            }.onFailure {
                YLog.error("DexKit native load failed: $it")
            }.getOrDefault(false)

            when {
                appInfo.dataDir != null -> initDexKitCache(appInfo.dataDir)
                else -> YLog.debug("Skipping DexKit cache init: appInfo.dataDir is null for ${appInfo.packageName}")
            }
        }

        HookRegistry.hooks.forEach { loadHooker(it) }
    }

    /**
     * Wires up [DexKitCacheBridge]'s global persistent cache. This must run
     * exactly once before any [DexKitCacheBridge.create] call. Since
     * `onHook()` runs once per hooked-app process (each scoped target app
     * gets its own process/classloader), a single global `init()` call here
     * is naturally scoped correctly without any extra guarding.
     */
    @OptIn(DexKitExperimentalApi::class)
    private fun initDexKitCache(targetAppDataDir: String) {
        if (!dexKitCacheInitCalled.compareAndSet(false, true)) {
            YLog.debug("DexKitCacheBridge already initialized, skipping")
            return
        }
        runCatching {
            // Keep the underlying native bridge alive a little longer than the
            // 5s default since a single app's hook setup (e.g. Instagram's
            // monet + ads hooks) can touch DexKit across a few different
            // Activity lifecycle callbacks in quick succession.
            DexKitCacheBridge.idleTimeoutMillis = 10_000L
            DexKitCacheBridge.cachePolicy = DexKitCacheBridge.CachePolicy(
                cacheSuccess = true,
                failurePolicy = DexKitCacheBridge.CacheFailurePolicy.NONE
            )
            DexKitCacheBridge.init(DexKitFileCache.create(targetAppDataDir))
        }.onFailure {
            YLog.error("DexKitCacheBridge init failed, falling back to uncached DexKitBridge: $it")
            dexKitCacheInitCalled.set(false)
        }
    }
}

private object HookRegistry {
    val hooks = listOf(
        FrameworkHook(),
        PinterestHooks(),
        RedditHooks(),
        SubstratumLiteHooks(),
        TwitterHooks(),
        FilesHooks(),
        GitHubHooks(),
        YoutubeHooks(),
        InstagramHooks()
    )
}