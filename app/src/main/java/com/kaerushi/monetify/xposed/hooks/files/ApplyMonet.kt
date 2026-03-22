package com.kaerushi.monetify.xposed.hooks.files

import com.highcapable.yukihookapi.hook.core.annotation.LegacyResourcesHook

object ApplyMonet {
    @OptIn(LegacyResourcesHook::class)
    fun FilesHooks.applyMonet() {
        resources().hook {
            injectResource {
                conditions {
                    name = "force_material3"
                    bool()
                }
                replaceToTrue()
            }
        }
    }
}