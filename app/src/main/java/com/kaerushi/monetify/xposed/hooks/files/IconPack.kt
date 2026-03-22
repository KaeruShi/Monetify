package com.kaerushi.monetify.xposed.hooks.files

import com.kaerushi.monetify.R
import com.kaerushi.monetify.xposed.hooks.DrawableReplacement
import com.kaerushi.monetify.xposed.utils.colorOnSurface
import com.kaerushi.monetify.xposed.utils.colorPrimary

object IconPack {
    val duotoneDrawables = mapOf(
        "ic_root_download" to DrawableReplacement(R.drawable.duotone_download),
        "ic_chip_image" to DrawableReplacement(R.drawable.duotone_image) {
            colorPrimary(it)
        },
        "ic_doc_image" to DrawableReplacement(R.drawable.duotone_image),
        "ic_doc_folder" to DrawableReplacement(R.drawable.duotone_folder),
        "ic_doc_generic" to DrawableReplacement(R.drawable.duotone_document),
        "ic_chip_document" to DrawableReplacement(R.drawable.duotone_document) {
            colorPrimary(it)
        },
        "ic_search_file_type" to DrawableReplacement(R.drawable.duotone_document),
        "ic_chip_audio" to DrawableReplacement(R.drawable.duotone_music_note) {
            colorPrimary(it)
        },
        "ic_doc_audio" to DrawableReplacement(R.drawable.duotone_headphone),
        "ic_doc_video" to DrawableReplacement(R.drawable.duotone_movie),
        "ic_chip_video" to DrawableReplacement(R.drawable.duotone_movie) {
            colorPrimary(it)
        },
        "ic_root_recent" to DrawableReplacement(R.drawable.duotone_time),
        "ic_root_recent_m3" to DrawableReplacement(R.drawable.duotone_time),
        "ic_eject" to DrawableReplacement(R.drawable.duotone_eject),
        "ic_menu_delete" to DrawableReplacement(R.drawable.duotone_delete),
        "ic_menu_delete_m3" to DrawableReplacement(R.drawable.duotone_delete),
        "ic_doc_apk" to DrawableReplacement(R.drawable.duotone_android),
        "ic_doc_codes" to DrawableReplacement(R.drawable.duotone_code),
        "ic_hamburger" to DrawableReplacement(R.drawable.duotone_menu) {
            colorOnSurface(it)
        },
        "ic_hamburger_m3" to DrawableReplacement(R.drawable.duotone_menu) {
            colorOnSurface(it)
        },
        "ic_done" to DrawableReplacement(R.drawable.duotone_check),
        "ic_done_m3" to DrawableReplacement(R.drawable.duotone_check),
        "ic_check" to DrawableReplacement(R.drawable.duotone_check),
        "ic_check_m3" to DrawableReplacement(R.drawable.duotone_check),
        "ic_check_circle" to DrawableReplacement(R.drawable.duotone_check_circle),
        "ic_check_circle_m3" to DrawableReplacement(R.drawable.duotone_check_circle),
        "ic_cancel" to DrawableReplacement(R.drawable.duotone_cross),
        "ic_cab_cancel" to DrawableReplacement(R.drawable.duotone_cross),
        "ic_cab_cancel_m3" to DrawableReplacement(R.drawable.duotone_cross),
        "ic_action_clear" to DrawableReplacement(R.drawable.duotone_cross),
        "ic_action_clear_m3" to DrawableReplacement(R.drawable.duotone_cross),
        "ic_m3_chip_close" to DrawableReplacement(R.drawable.duotone_cross),
        "ic_menu_search" to DrawableReplacement(R.drawable.duotone_search),
        "ic_menu_search_m3" to DrawableReplacement(R.drawable.duotone_search),
        "abc_ic_menu_overflow_material" to DrawableReplacement(R.drawable.duotone_more_vert)
    )
}