package com.kaerushi.monetify.xposed.hooks.reddit

import com.kaerushi.monetify.xposed.MainHook.bridge
import org.luckypray.dexkit.query.enums.StringMatchType

fun RedditHooks.disableAds() {
    val commentAds = bridge.findMethod {
        matcher {
            name("invokeSuspend")
            declaredClass("LoadAdsCombinedCall", StringMatchType.Contains)
        }
    }
    commentAds.single().getMethodInstance(appClassLoader!!).hook {
        before {
            result = args[0]
        }
    }

    val apsMethod = bridge.findClass {
        matcher {
            usingStrings("AdPostSection(linkId=")
        }
    }.single().methods.single { it.isConstructor }
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
