package org.sjhstudio.lostark.util

import android.widget.ImageView
import androidx.core.content.ContextCompat
import org.sjhstudio.lostark.R

fun ImageView.setCardBackground(grade: String) {
    foreground = when (grade) {
        "전설" -> ContextCompat.getDrawable(context, R.drawable.img_card_legend)
        "영웅" -> ContextCompat.getDrawable(context, R.drawable.img_card_hero)
        "희귀" -> ContextCompat.getDrawable(context, R.drawable.img_card_rare)
        "고급" -> ContextCompat.getDrawable(context, R.drawable.img_card_high)
        else -> ContextCompat.getDrawable(context, R.drawable.img_card_normal)
    }
}