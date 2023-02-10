package org.sjhstudio.lostark.util

import android.content.Context

fun Context.dpToPx(dp: Int) = dp * resources.displayMetrics.density

fun Context.pxToDp(px: Float) = (px / resources.displayMetrics.density).toInt()