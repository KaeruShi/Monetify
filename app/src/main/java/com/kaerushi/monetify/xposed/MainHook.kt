package com.kaerushi.monetify.xposed

import com.highcapable.yukihookapi.annotation.xposed.InjectYukiHookWithXposed
import com.highcapable.yukihookapi.hook.factory.encase
import com.highcapable.yukihookapi.hook.xposed.proxy.IYukiHookXposedInit
import com.kaerushi.monetify.R
import com.kaerushi.monetify.data.PINTEREST_PACKAGE_NAME
import com.kaerushi.monetify.data.REDDIT_PACKAGE_NAME
import com.kaerushi.monetify.data.SUBSTRATUM_LITE_PACKAGE_NAME
import com.kaerushi.monetify.xposed.hooks.PinterestHooks
import com.kaerushi.monetify.xposed.hooks.RedditHooks
import com.kaerushi.monetify.xposed.hooks.SubstratumLiteHooks

@InjectYukiHookWithXposed
class MainHook : IYukiHookXposedInit {
    override fun onHook() = encase {
        loadApp(name = PINTEREST_PACKAGE_NAME, PinterestHooks)
        loadApp(name = REDDIT_PACKAGE_NAME, RedditHooks)
        loadApp(name = SUBSTRATUM_LITE_PACKAGE_NAME) {
            resources().hook {
                injectResource {
                    conditions {
                        name = "no_theme_title"
                        string()
                    }
                    replaceTo("kontol")
                }
            }
        }
    }
}