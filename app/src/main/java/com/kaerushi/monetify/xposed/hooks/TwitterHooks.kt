package com.kaerushi.monetify.xposed.hooks

import android.app.Activity
import com.highcapable.yukihookapi.hook.core.annotation.LegacyResourcesHook
import com.kaerushi.monetify.R
import com.kaerushi.monetify.data.X_PACKAGE_NAME
import com.kaerushi.monetify.xposed.utils.ColorUtils.colorPrimary
import com.kaerushi.monetify.xposed.utils.ColorUtils.colorSurface
import com.kaerushi.monetify.xposed.utils.ColorUtils.colorSurfaceContainer
import com.kaerushi.monetify.xposed.utils.ColorUtils.colorSurfaceContainerDark
import com.kaerushi.monetify.xposed.utils.ColorUtils.colorSurfaceContainerLight
import com.kaerushi.monetify.xposed.utils.PreferenceUtil.getAppMonetEnabled
import com.kaerushi.monetify.xposed.utils.Utils.isNightMode

object TwitterHooks : BaseAppHook() {
    override val pkgName: String = X_PACKAGE_NAME
    override val duotoneDrawables: Map<String, Int> = mapOf(
        "ic_vector_home_stroke" to R.drawable.duotone_home,
        "ic_vector_home" to R.drawable.duotone_home_fill,
        "ic_vector_search" to R.drawable.duotone_search_fill,
        "ic_vector_search_stroke" to R.drawable.duotone_search,
        "ic_vector_grok_icon" to R.drawable.duotone_grok,
        "ic_vector_grok_icon_fill" to R.drawable.duotone_grok_fill,
        "ic_vector_notifications" to R.drawable.duotone_notification_fill,
        "ic_vector_notifications_stroke" to R.drawable.duotone_notification,
        "ic_vector_messages" to R.drawable.duotone_mail_fill,
        "ic_vector_messages_stroke" to R.drawable.duotone_mail,
        "ic_vector_overflow" to R.drawable.duotone_more_vert_24,
        "ic_vector_overflow_cricle" to R.drawable.duotone_more_vert_circle,
        "ic_vector_more" to R.drawable.duotone_more,
        "ic_vector_more_circle" to R.drawable.duotone_more_circle,
        "ic_vector_bookmark" to R.drawable.duotone_bookmark,
        "ic_vector_bookmark_stroke" to R.drawable.duotone_bookmark_fill,
        "ic_vector_reply" to R.drawable.duotone_comment_fill,
        "ic_vector_reply_stroke" to R.drawable.duotone_comment,
        "ic_vector_reply_stroke_bold" to R.drawable.duotone_comment,
        "ic_vector_retweet" to R.drawable.duotone_repost,
        "ic_vector_retweet_stroke" to R.drawable.duotone_repost,
        "ic_vector_heart" to R.drawable.duotone_heart_fill,
        "ic_vector_heart_stroke" to R.drawable.duotone_heart,
        "ic_vector_share_android" to R.drawable.duotone_share_android,
        "ic_vector_plus" to R.drawable.duotone_plus,
        "ic_vector_medium_plus" to R.drawable.duotone_plus,
        "ic_vector_plus_circle" to R.drawable.duotone_plus_circle,
        "ic_vector_plus_circle_fill" to R.drawable.duotone_plus_circle_fill,
        "ic_vector_close" to R.drawable.duotone_cross,
        "ic_vector_compose_dm" to R.drawable.duotone_mail_add,
        "ic_vector_bar_chart" to R.drawable.duotone_insight,
        "ic_vector_bar_chart_bold" to R.drawable.duotone_insight,
        "ic_vector_settings" to R.drawable.duotone_settings,
        "ic_vector_settings_stroke" to R.drawable.duotone_settings,
        "ic_vector_frown_circle" to R.drawable.duotone_emoji_frown,
        "ic_vector_no" to R.drawable.duotone_block,
        "ic_vector_follow" to R.drawable.duotone_user_add,
        "ic_vector_people_group_stroke" to R.drawable.duotone_user_group,
        "ic_vector_follow_close_stroke" to R.drawable.duotone_user_cross,
        "ic_vector_following_stroke" to R.drawable.duotone_user_follow,
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
        "muted" to R.drawable.duotone_volume_off,
        "ic_vector_filter" to R.drawable.duotone_option,
        "ic_vector_filter_fill" to R.drawable.duotone_option,
        "ic_vector_bar_chart_horizontal_stroke" to R.drawable.duotone_poll,
        "ic_vector_bar_chart_horizontal" to R.drawable.duotone_poll,
        "ic_vector_pencil_stroke" to R.drawable.duotone_pencil_edit,
        "ic_vector_pencil_edit" to R.drawable.duotone_pencil,
        "ic_vector_camera_stroke" to R.drawable.duotone_camera,
        "ic_vector_medium_camera_stroke" to R.drawable.duotone_camera,
        "ic_vector_medium_camera_stroke_tint" to R.drawable.duotone_camera,
        "ic_vector_gif_pill_stroke" to R.drawable.duotone_gif,
        "ic_vector_location_stroke" to R.drawable.duotone_location,
        "ic_vector_photo_stroke" to R.drawable.duotone_image,
        "ic_vector_globe" to R.drawable.duotone_globe_fill,
        "ic_vector_globe_stroke" to R.drawable.duotone_globe,
        "ic_vector_camera_video_stroke" to R.drawable.duotone_record_play,
        "ic_vector_camera_video_stroke_calls" to R.drawable.duotone_record,
        "ic_vector_compose_spaces" to R.drawable.duotone_spaces,
        "ic_vector_mic_outline" to R.drawable.duotone_mic,
        "ic_room_mic" to R.drawable.duotone_mic,
        "ic_vector_compose" to R.drawable.duotone_compose_feather,
        "ic_vector_help_circle" to R.drawable.duotone_help,
        "ic_vector_link" to R.drawable.duotone_link,
        "ic_vector_at" to R.drawable.duotone_at,
        "ic_vector_at_bold" to R.drawable.duotone_at,
        "ic_vector_browser_globe" to R.drawable.duotone_web,
        "ic_vector_calendar" to R.drawable.duotone_calendar,
        "ic_vector_star_rising" to R.drawable.duotone_star_shooting
    )

    override fun hookOnCreate(instance: Activity) {
        if (getAppMonetEnabled(pkgName)) applyMonetMainActivity(instance)
    }

    @OptIn(LegacyResourcesHook::class)
    private fun applyMonetMainActivity(activity: Activity) {
        injectColor(
            "twitter_blue_opacity_30",
            "twitter_blue_opacity_50",
            "twitter_blue_opacity_58",
            "blue_500",
            "twitter_blue",
            "text_black"
        ) {
            colorPrimary(activity)
        }
        injectColor("appBackground", "ps__black") {
            colorSurface(activity)
        }
        injectColor("black") {
            if (isNightMode(activity)) colorSurfaceContainerDark(activity) else colorSurfaceContainerLight(activity)
        }
        if (!isNightMode(activity))
            injectColor("white") {
                colorSurfaceContainer(activity)
            }
    }
}