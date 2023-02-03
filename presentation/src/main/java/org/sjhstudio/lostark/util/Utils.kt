package org.sjhstudio.lostark.util

import android.widget.ImageView
import androidx.core.content.ContextCompat
import org.sjhstudio.lostark.R

fun ImageView.setItemBackground(grade: String) {
    background = when (grade) {
        "고대" -> ContextCompat.getDrawable(context, R.drawable.bg_item_ancient)
        "유물" -> ContextCompat.getDrawable(context, R.drawable.bg_item_relic)
        "전설" -> ContextCompat.getDrawable(context, R.drawable.bg_item_legend)
        "영웅" -> ContextCompat.getDrawable(context, R.drawable.bg_item_hero)
        "희귀" -> ContextCompat.getDrawable(context, R.drawable.bg_item_rare)
        "고급" -> ContextCompat.getDrawable(context, R.drawable.bg_item_high)
        else -> ContextCompat.getDrawable(context, R.drawable.bg_item_default)
    }
}