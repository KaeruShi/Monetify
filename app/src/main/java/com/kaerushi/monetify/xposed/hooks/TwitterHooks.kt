package com.kaerushi.monetify.xposed.hooks

import com.highcapable.yukihookapi.hook.core.annotation.LegacyResourcesHook
import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import com.kaerushi.monetify.R
import com.kaerushi.monetify.data.X_PACKAGE_NAME
import com.kaerushi.monetify.data.viewmodel.AppIconPack
import com.kaerushi.monetify.xposed.utils.PreferenceUtil.getAppIconPack

object TwitterHooks : YukiBaseHooker() {
    @LegacyResourcesHook
    override fun onHook() {
        val pkg = X_PACKAGE_NAME
        loadApp(pkg) {
            val duotoneDrawables = mapOf(
                "ic_vector_home_stroke" to R.drawable.duotone_home,
                "ic_vector_home" to R.drawable.duotone_home,
                "ic_vector_search" to R.drawable.duotone_search,
                "ic_vector_search_stroke" to R.drawable.duotone_search,
                "ic_vector_grok_icon" to R.drawable.duotone_grok,
                "ic_vector_grok_icon_filled" to R.drawable.duotone_grok_filled,
                "ic_vector_notifications" to R.drawable.duotone_notification,
                "ic_vector_notifications_stroke" to R.drawable.duotone_notification,
                "ic_vector_messages" to R.drawable.duotone_mail,
                "ic_vector_messages_stroke" to R.drawable.duotone_mail,
                "ic_vector_overflow" to R.drawable.duotone_more_vert_24,
                "ic_vector_more" to R.drawable.duotone_more,
                "ic_vector_more_circle" to R.drawable.duotone_more_circle,
                "ic_vector_bookmark" to R.drawable.duotone_bookmark,
                "ic_vector_bookmark_stroke" to R.drawable.duotone_bookmark,
                "ic_vector_reply" to R.drawable.duotone_comment,
                "ic_vector_reply_stroke" to R.drawable.duotone_comment,
                "ic_vector_reply_stroke_bold" to R.drawable.duotone_comment,
                "ic_vector_retweet" to R.drawable.duotone_repost,
                "ic_vector_retweet_stroke" to R.drawable.duotone_repost,
                "ic_vector_heart" to R.drawable.duotone_heart_filled,
                "ic_vector_heart_stroke" to R.drawable.duotone_heart,
                "ic_vector_share_android" to R.drawable.duotone_share,
                "ic_vector_plus" to R.drawable.duotone_plus,
                "ic_vector_medium_plus" to R.drawable.duotone_plus,
                "ic_vector_close" to R.drawable.duotone_cross,
                "ic_vector_compose_dm" to R.drawable.duotone_mail_add,
                "ic_vector_bar_chart" to R.drawable.duotone_insight,
                "ic_vector_bar_chart_bold" to R.drawable.duotone_insight,
                "ic_vector_settings" to R.drawable.duotone_settings,
                "ic_vector_settings_stroke" to R.drawable.duotone_settings,
                "ic_vector_frown_circle" to R.drawable.duotone_emoji_frown,
                "ic_vector_no" to R.drawable.duotone_block,
                "ic_vector_follow" to R.drawable.duotone_add_user,
                "ic_vector_compose_lists" to R.drawable.duotone_list_add,
                "ic_vector_speaker_off" to R.drawable.duotone_mute,
                "ic_vector_megaphone_stroke" to R.drawable.duotone_broadcast,
                "ic_vector_flag" to R.drawable.duotone_report_flag,
                "ic_vector_flag_fill" to R.drawable.duotone_report_flag,
                "ic_vector_sound_off" to R.drawable.duotone_volume_zero,
                "ic_vector_arrow_left" to R.drawable.duotone_arrow_back,
                "ic_vector_media_dock" to R.drawable.duotone_media_dock,
                "ic_vector_media_playback_speed" to R.drawable.duotone_playback_speed,
                "unmuted" to R.drawable.duotone_volume_full,
                "muted" to R.drawable.duotone_volume_off
            )

            val iconPack = when (getAppIconPack(pkg)) {
                AppIconPack.DEFAULT.name -> null
                AppIconPack.DUOTONE.name -> duotoneDrawables
                else -> null
            }

            resources().hook {
                iconPack?.forEach { (resourceName, replacementDrawable) ->
                    injectResource {
                        conditions {
                            name = resourceName
                            drawable()
                        }
                        replaceToModuleResource(replacementDrawable)
                    }
                }
            }
        }
    }
}