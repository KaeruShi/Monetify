package com.kaerushi.monetify.xposed.hooks.reddit

import com.kaerushi.monetify.xposed.MainHook.bridge

object AdsHook {
    fun RedditHooks.disableAds() {
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
}