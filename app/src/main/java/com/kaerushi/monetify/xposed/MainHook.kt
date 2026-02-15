package com.kaerushi.monetify.xposed

import com.highcapable.yukihookapi.annotation.xposed.InjectYukiHookWithXposed
import com.highcapable.yukihookapi.hook.factory.encase
import com.highcapable.yukihookapi.hook.xposed.proxy.IYukiHookXposedInit
import com.kaerushi.monetify.xposed.hooks.PinterestHooks
import com.kaerushi.monetify.xposed.hooks.RedditHooks
import com.kaerushi.monetify.xposed.hooks.SubstratumLiteHooks
import com.kaerushi.monetify.xposed.hooks.TwitterHooks

@InjectYukiHookWithXposed(isUsingResourcesHook = true)
object MainHook : IYukiHookXposedInit {
    override fun onHook() = encase {
        HookRegistry.hooks.forEach { loadHooker(it) }
    }
}

private object HookRegistry {
    val hooks = listOf(
        PinterestHooks,
        RedditHooks,
        SubstratumLiteHooks,
        TwitterHooks
    )
}