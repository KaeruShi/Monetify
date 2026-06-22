package com.kaerushi.monetify.xposed.hooks.instagram

import com.highcapable.yukihookapi.hook.log.YLog
import org.luckypray.dexkit.annotations.DexKitExperimentalApi

@DexKitExperimentalApi
fun InstagramHooks.disableAds() {
    val fingerprintAdsList = bridgeCache.getMethods("instagram_disable_ads_fingerprint_ads") {
        matcher {
            returnType = "void"
            usingStrings("SponsoredContentController")
        }
    }

    for (methodData in fingerprintAdsList) {

        try {
            val methodInstance = methodData.getMethodInstance(appClassLoader!!)
            methodInstance.hook {
                replaceUnit {}
            }
        } catch (e: Throwable) {
            YLog.error("Hook assignment failed for ${methodData.name}: ${e.message}")
        }
    }
}