package com.kaerushi.monetify.xposed.hooks.instagram

import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.highcapable.kavaref.KavaRef.Companion.resolve
import com.highcapable.yukihookapi.hook.log.YLog
import com.kaerushi.monetify.xposed.MainHook.bridge
import com.kaerushi.monetify.xposed.utils.colorPrimary
import com.kaerushi.monetify.xposed.utils.colorSurface
import com.kaerushi.monetify.xposed.utils.colorSurfaceContainer

fun InstagramHooks.applyMonetRes(instance: Activity) {
    injectColor("igds_prism_black") {
        colorPrimary(instance)
    }
}

fun InstagramHooks.applyMonetClazz() {
    val profileTabClass = bridge.findClass {
        matcher {
            fields {
                count(4)
                add {
                    type("com.google.android.material.tabs.TabLayout")
                }
                add {
                    type(ValueAnimator::class.java)
                }
            }
        }
    }.single()

    View::class.java.resolve().method { name = "onAttachedToWindow" }.hookAll {
        before {
            fun shouldSkip(v: View, vararg names: String): Boolean {
                val id = v.id
                if (id == View.NO_ID) return false

                return try {
                    val entryName = v.resources.getResourceEntryName(id)
                    names.contains(entryName)
                } catch (e: Exception) {
                    false
                }
            }

            val view = instance as View
            when (view.javaClass.name) {

                // Base layout
                "com.instagram.common.ui.base.IgLinearLayout" -> {
                    if (!shouldSkip(
                            view,
                            "pog_note_bubble_root_view",
                            "pog_bubble_text_container",
                            "pog_music_note_content_container_view",
                            "clips_viewer_action_bar_title_container",
                            "clips_action_bar_start_action_buttons",
                            "clips_action_bar_end_action_buttons",
                            "clips_attached_scrubber_container",
                            "action_bar_LinearLayout",
                            "avatar_front_background",
                            "profile_attribution_container",
                            "title_container",
                            "caption",
                            "caption_container",
                            "suggested_entity_card_container",
                            "suggested_entity_card_social_context_container",
                            "direct_text_message_text_parent",
                            "message_composer_reply_bar_container",
                            "feed_preview_cta_container",
                            "feed_preview_bottom_cta_container",
                            "grid_view_pog_text_view_container"
                        )
                    ) {
                        setViewBackgroundColor(
                            view,
                            colorSurface(appContext!!)
                        )
                    }
                    if (shouldSkip(view, "row_thread_composer_textarea_container")) {
                        setViewBackgroundColor(
                            view,
                            colorSurfaceContainer(appContext!!),
                            radius = floatToDp(appContext!!, 60f)
                        )
                    }
                    if (shouldSkip(view, "composer_content_container")) {
                        setViewBackgroundColor(
                            view,
                            colorSurfaceContainer(appContext!!)
                        )
                    }
                    if (shouldSkip(view, "direct_text_message_text_parent")) {
                        setViewBackgroundColor(
                            view,
                            colorSurfaceContainer(appContext!!),
                            radius = floatToDp(appContext!!, 60f)
                        )
                    }
                }

                "com.instagram.common.ui.base.IgFrameLayout" -> {
                    if (shouldSkip(view, "bottom_sheet_drag_handle_frame", "newsfeed_you")) {
                        setViewBackgroundColor(
                            view,
                            colorSurface(appContext!!)
                        )
                    }
                    if (shouldSkip(view, "direct_private_share_action_bar_container_view")) {
                        if (view is ViewGroup) {
                            val child = view.getChildAt(0) as View
                            child.setBackgroundColor(Color.RED)
                        }
                    }
                    if (shouldSkip(view, "direct_private_share_recipients_container")) {
                        if (view is ViewGroup) {
                            for (i in 0 until view.childCount) {
                                val child = view.getChildAt(i)

                                YLog.debug(
                                    "child[$i] = ${child.javaClass.name}"
                                )
                                child.setBackgroundColor(Color.BLUE)
                            }

                        }
                    }
                }

                "com.instagram.common.ui.widget.recyclerview.MainFeedRecyclerView" -> {
                    setViewBackgroundColor(
                        view,
                        colorSurface(appContext!!)
                    )
                }

                "com.instagram.commoun.ui.widget.recyclerview.FlywheelCompatibleRecyclerView" -> {
                    setViewBackgroundColor(
                        view,
                        colorSurface(appContext!!)
                    )
                }

                "com.instagram.common.ui.widget.touchinterceptorlayout.TouchInterceptorLinearLayout" -> {
                    setViewBackgroundColor(
                        view,
                        colorSurface(appContext!!)
                    )
                }

                "com.instagram.common.ui.widget.adapterlayout.AdapterLinearLayout" -> {
                    setViewBackgroundColor(
                        view,
                        colorSurface(appContext!!)
                    )
                }

                "com.instagram.ui.widget.loadmore.LoadMoreButton" -> {
                    if (shouldSkip(view, "row_load_more_button")) {
                        setViewBackgroundColor(
                            view,
                            colorSurface(appContext!!)
                        )
                    }
                }

                ConstraintLayout::class.java.name -> {
                    if (shouldSkip(
                            view,
                            "nav_buttons_and_title_container", "similar_accounts_carousel_header",
                            "direct_private_share_container_view"
                        )
                    ) setViewBackgroundColor(
                        view,
                        colorSurface(appContext!!)
                    )
                    if (shouldSkip(view, "direct_share_sheet_grid_view_pog")) {
                        val parent = view.getParent(1) as View
                        setViewBackgroundColor(parent, Color.BLUE)
                    }
                }

                "com.instagram.ui.widget.nestablescrollingview.NestableRecyclerView" -> {
                    setViewBackgroundColor(
                        view,
                        colorSurface(appContext!!)
                    )
                }

                RecyclerView::class.java.name -> {
                    if (shouldSkip(view, "direct_external_reshare_row")) setViewBackgroundColor(
                        view,
                        colorSurface(appContext!!)
                    )
                }

                // Text
                "com.instagram.common.ui.base.IgTextView" -> {
                    if (shouldSkip(view, "header_action_button")) {
                        setViewBackgroundColor(
                            view,
                            colorSurface(appContext!!)
                        )
                    }
                    if (shouldSkip(view, "pog_music_note_song_title_text")) {
                        val parent = view.parent as View
                        setViewBackgroundColor(
                            parent,
                            Color.TRANSPARENT,
                        )
                    }
                    if (shouldSkip(view, "title_text")) {
                        val parent = view.parent as View
                        setViewBackgroundColor(
                            parent,
                            Color.TRANSPARENT,
                        )
                    }
                    if (shouldSkip(view, "comment_composer_text_view")) {
                        val firstParent = view.parent as View
                        val secondParent = firstParent.parent as View
                        setViewBackgroundColor(
                            firstParent,
                            colorSurfaceContainer(appContext!!),
                            radius = floatToDp(appContext!!, 60f)
                        )
                        setViewBackgroundColor(
                            secondParent,
                            colorSurface(appContext!!)
                        )
                    }
                    if (shouldSkip(view, "text1")) {
                        val parent = view.parent as View
                        setViewBackgroundColor(
                            parent,
                            Color.TRANSPARENT,
                        )
                    }
                }

                // Spacer
                "com.instagram.common.ui.base.IgView" -> {
                    if (shouldSkip(view, "footer_space")) setViewBackgroundColor(
                        view,
                        colorSurface(appContext!!)
                    )
                }

                // Reaction Search Box
                "com.instagram.igds.components.search.IgdsInlineSearchBox" -> {
                    setViewBackgroundColor(
                        view,
                        colorSurface(appContext!!)
                    )
                }

                // Action bar
                "com.instagram.igds.components.actionbar.IgdsActionBar" -> {
                    setViewBackgroundColor(
                        view,
                        colorSurface(appContext!!)
                    )
                }

                "com.instagram.profile.actionbar.ProfileActionBar" -> {
                    setViewBackgroundColor(
                        view,
                        colorSurface(appContext!!)
                    )
                }

                "com.instagram.discovery.actionbar.ExploreActionBar" -> {
                    setViewBackgroundColor(
                        view,
                        colorSurface(appContext!!)
                    )
                }

                "com.instagram.ui.widget.searchedittext.SearchEditText" -> {
                    setViewBackgroundColor(
                        view,
                        colorSurfaceContainer(appContext!!), radius = floatToDp(appContext!!, 6f)
                    )
                }

                // Tab
                TabLayout::class.java.name -> {
                    if (!shouldSkip(view, "action_bar_tab_layout")) setViewBackgroundColor(
                        view,
                        colorSurface(appContext!!)
                    )
                }

                // Card
                "com.instagram.ui.widget.roundedcornerlayout.RoundedCornerFrameLayout" -> {
                    setViewBackgroundColor(
                        view,
                        colorSurfaceContainer(appContext!!),
                        radius = floatToDp(appContext!!, 12f)
                    )
                }

                // Follow button
                "com.instagram.user.follow.FollowButton" -> {
                    val reelsParent = view.getParent(3)
                    if (reelsParent?.javaClass?.name != "com.facebook.litho.ComponentHost")
                        setViewBackgroundColor(
                            view,
                            colorSurfaceContainer(appContext!!),
                            radius = floatToDp(appContext!!, 8f)
                        )
                }

                // Context menu
                "com.instagram.ui.widget.roundedcornerlayout.RoundedCornerLinearLayout" -> {
                    setViewBackgroundColor(
                        view,
                        colorSurfaceContainer(appContext!!),
                        radius = floatToDp(appContext!!, 16f)
                    )
                }

                // Bottom sheet
                "com.instagram.common.ui.widget.prioritizedverticallayout.IgPrioritizedVerticalLayout" -> {
                    setViewBackgroundColor(
                        view,
                        colorSurface(appContext!!),
                        radius = floatToDp(appContext!!, 24f)
                    )
                }

                // System bar
                "com.instagram.ui.swipenavigation.container.SwipeNavigationContainer" -> {
                    setViewBackgroundColor(
                        view,
                        colorSurface(appContext!!)
                    )
                }
            }
        }
    }

    // Button
    "com.instagram.igds.components.button.IgdsButton".toClass().resolve()
        .method {
            name = "onDraw"
            parameterCount = 1
        }.hookAll {
            after {
                val canvas = args[0] as Canvas
                val view = instance as View
                val paint = Paint().apply {
                    color = colorSurfaceContainer(appContext!!)
                    isAntiAlias = true
                }
                val rect = RectF(0f, 0f, view.width.toFloat(), view.height.toFloat())
                canvas.drawRoundRect(rect, 24f, 24f, paint)
            }
        }

    val chainButtonMethod = bridge.findMethod {
        matcher {
            usingStrings("progressBar", "buttonImageView")
            declaredClass {
                superClass("android.widget.FrameLayout")
                fields {
                    add { type("android.widget.ImageView") }
                    add { type("android.widget.ProgressBar") }
                }
            }
        }
    }.single()

    val iconField = bridge.findField {
        matcher {
            declaredClass(chainButtonMethod.className)
            type("android.widget.ImageView")
        }
    }.single()

    chainButtonMethod.getMethodInstance(appClassLoader!!).hook {
        after {
            val button = instance as View
            val iconView = iconField
                .getFieldInstance(appClassLoader!!)
                .get(button) as? View ?: return@after
            iconView.background = GradientDrawable().apply {
                setColor(colorSurfaceContainer(appContext!!))
                cornerRadius = floatToDp(appContext!!, 6f)
            }
        }
    }

}

fun setViewBackgroundColor(view: View, color: Int, radius: Float = 0f) {
    fun apply() {
        if (radius > 0f) applyToViewWithRadius(view, color, radius)
        else applyToView(view, color)
    }

    apply()

    // Use a self-removing listener instead of accumulating them
    val listener = ViewTreeObserver.OnGlobalLayoutListener {
        apply()
    }

    view.viewTreeObserver.addOnGlobalLayoutListener(listener)

    // Remove listener when view detaches to avoid leaks + stale reapplications
    view.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
        override fun onViewAttachedToWindow(v: View) {}
        override fun onViewDetachedFromWindow(v: View) {
            v.viewTreeObserver.removeOnGlobalLayoutListener(listener)
            v.removeOnAttachStateChangeListener(this)
        }
    })
}

private fun applyToView(view: View, color: Int) {
    view.setBackgroundColor(color)
}

private fun applyToViewWithRadius(view: View, color: Int, radius: Float = 0f) {
    view.background = GradientDrawable().apply {
        setColor(color)
        cornerRadius = radius
    }
}

fun View.getParent(level: Int): View? {
    var p: View? = this
    repeat(level) {
        p = p?.parent as? View
    }
    return p
}

fun floatToDp(context: Context, value: Float): Float = value * context.resources.displayMetrics.density