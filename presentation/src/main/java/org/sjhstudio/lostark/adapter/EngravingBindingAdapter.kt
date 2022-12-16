package org.sjhstudio.lostark.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("engravingLevel")
fun TextView.bindEngravingLevel(fullName: String?) {
    if (!fullName.isNullOrEmpty()) {
        text = fullName.substring(fullName.length - 1)
    }
}

@BindingAdapter("engravingName")
fun TextView.bindingEngravingName(fullName: String?) {
    if (!fullName.isNullOrEmpty()) {
        text = fullName.substring(0, fullName.length - 6)
    }
}
