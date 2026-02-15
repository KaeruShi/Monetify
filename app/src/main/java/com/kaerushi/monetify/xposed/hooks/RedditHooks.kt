package com.kaerushi.monetify.xposed.hooks

import com.kaerushi.monetify.R
import com.kaerushi.monetify.data.REDDIT_PACKAGE_NAME

object RedditHooks : BaseAppHook() {
    override val pkgName: String = REDDIT_PACKAGE_NAME
    override val duotoneDrawables: Map<String, Int> = mapOf(
        "icon_home" to R.drawable.duotone_home,
        "icon_home_fill" to R.drawable.duotone_home_filled,
        "icon_home_24" to R.drawable.duotone_home,
        "icon_home_24_fill" to R.drawable.duotone_home_filled,
        "icon_answers" to R.drawable.duotone_answer,
        "icon_answers_fill" to R.drawable.duotone_answer_filled,
        "icon_answers_24" to R.drawable.duotone_answer,
        "icon_answers_24_fill" to R.drawable.duotone_answer_filled,
        "icon_add_square" to R.drawable.duotone_plus_rect,
        "icon_add_square_fill" to R.drawable.duotone_plus_rect_filled,
        "icon_add_square_24" to R.drawable.duotone_plus_rect,
        "icon_add_square_24_fill" to R.drawable.duotone_plus_rect_filled,
        "icon_chat" to R.drawable.duotone_chat,
        "icon_chat_fill" to R.drawable.duotone_chat_filled,
        "icon_chat_24" to R.drawable.duotone_chat,
        "icon_chat_24_fill" to R.drawable.duotone_chat_filled,
        "icon_notifications" to R.drawable.duotone_notification,
        "icon_notifications_fill" to R.drawable.duotone_notification_filled,
        "icon_notifications_24" to R.drawable.duotone_notification,
        "icon_notifications_24_fill" to R.drawable.duotone_notification_filled,
        "icon_search" to R.drawable.duotone_search,
        "icon_search_fill" to R.drawable.duotone_search,
        "icon_overflow_horizontal" to R.drawable.duotone_more_24,
        "icon_overflow_horizontal_fill" to R.drawable.duotone_more_24,
        "icon_overflow_vertical" to R.drawable.duotone_more_vert_24,
        "icon_overflow_vertical_fill" to R.drawable.duotone_more_vert_24,
        "icon_close" to R.drawable.duotone_cross,
        "icon_close_fill" to R.drawable.duotone_cross,
        "icon_filter" to R.drawable.duotone_option,
        "icon_filter_fill" to R.drawable.duotone_option,
        "icon_settings" to R.drawable.duotone_settings
    )
}