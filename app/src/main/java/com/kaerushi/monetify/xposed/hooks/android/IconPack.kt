package com.kaerushi.monetify.xposed.hooks.android

import com.kaerushi.monetify.R
import com.kaerushi.monetify.xposed.hooks.DrawableReplacement
import com.kaerushi.monetify.xposed.utils.colorOnSurface
import com.kaerushi.monetify.xposed.utils.colorPrimary

object IconPack {
    val duotoneDrawables = mapOf(
        "ic_doc_image" to DrawableReplacement(R.drawable.duotone_image),
        "ic_doc_folder" to DrawableReplacement(R.drawable.duotone_folder),
        "ic_doc_generic" to DrawableReplacement(R.drawable.duotone_document),
        "ic_doc_audio" to DrawableReplacement(R.drawable.duotone_headphone),
        "ic_doc_video" to DrawableReplacement(R.drawable.duotone_movie),
        "ic_doc_apk" to DrawableReplacement(R.drawable.duotone_android),
        "ic_doc_codes" to DrawableReplacement(R.drawable.duotone_code)
    )
}