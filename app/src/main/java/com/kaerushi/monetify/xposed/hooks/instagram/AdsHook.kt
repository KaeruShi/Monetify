package com.kaerushi.monetify.xposed.hooks.instagram

import com.highcapable.yukihookapi.hook.log.YLog

fun InstagramHooks.disableAds() {
    val fingerprintAdsList = bridge.findMethod {
        matcher {
            returnType = "void"
            usingStrings("SponsoredContentController")
        }
    }.toList()

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