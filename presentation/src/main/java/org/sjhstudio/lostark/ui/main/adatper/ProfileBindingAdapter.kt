package org.sjhstudio.lostark.ui.main.adatper

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter

/**
 * @description : 영지 이름
 * @example : Lv.41 아가마을
 */
@SuppressLint("SetTextI18n")
@BindingAdapter(value = ["townLevel", "townName"])
fun TextView.bindTownFullName(townLevel: String?, townName: String?) {
    if (!townLevel.isNullOrEmpty() && !townName.isNullOrEmpty()) {
        text = if (townLevel != "null") "Lv.$townLevel $townName" else townName
    }
}