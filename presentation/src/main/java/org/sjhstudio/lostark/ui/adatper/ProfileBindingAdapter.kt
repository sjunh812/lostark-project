package org.sjhstudio.lostark.ui.adatper

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter

@SuppressLint("SetTextI18n")
@BindingAdapter(value = ["townLevel", "townName"])
fun TextView.bindTownFullName(townLevel: String?, townName: String?) {
    if (!townLevel.isNullOrEmpty() && !townName.isNullOrEmpty()) {
        text = if (townLevel != "null") "Lv.$townLevel $townName" else townName
    }
}