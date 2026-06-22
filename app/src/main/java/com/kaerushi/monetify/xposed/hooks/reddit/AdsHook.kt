package com.kaerushi.monetify.xposed.hooks.reddit

import org.luckypray.dexkit.query.enums.StringMatchType

fun RedditHooks.disableAds() {
    val commentAdsMethod = bridgeCache.getMethodDirect("reddit_disable_ads_comment_ads") {
        findClass {
            matcher {
                className("LoadAdsCombinedCall", StringMatchType.Contains)
            }
        }.single().findMethod {
            matcher {
                name("invokeSuspend")
            }
        }.single()
    }
    commentAdsMethod.getMethodInstance(appClassLoader!!).hook {
        before {
            result = args[0]
        }
    }

    val apsMethod = bridgeCache.getMethodDirect("reddit_disable_ads_aps_constructor") {
        findClass {
            matcher {
                usingStrings("AdPostSection(linkId=")
            }
        }.single().methods.single { it.isConstructor }
    }
    val apsInit = apsMethod.getConstructorInstance(appClassLoader!!)
    val arg = apsInit.parameters.indexOfFirst { MutableList::class.java.isAssignableFrom(it.type) }
    apsInit.hook {
        before {
            val sections = args[arg] as MutableList<*>
            sections.javaClass
                .declaredFields
                .first { it.type == Array<Any>::class.java }
                .apply { isAccessible = true }
                .set(sections, emptyArray<Any>())
        }
    }
}
