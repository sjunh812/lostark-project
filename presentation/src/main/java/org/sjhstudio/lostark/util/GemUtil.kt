package org.sjhstudio.lostark.util

import android.annotation.SuppressLint
import android.widget.TextView
import org.sjhstudio.lostark.domain.model.response.Gem

@SuppressLint("SetTextI18n")
fun TextView.setGemName(gemInfo: Gem.GemInfo) {
    val type = when (gemInfo.priority) {
        0 -> "멸"
        1 -> "홍"
        2 -> "청"
        3 -> "원"
        else -> ""
    }
    text = "${gemInfo.level}$type"
}