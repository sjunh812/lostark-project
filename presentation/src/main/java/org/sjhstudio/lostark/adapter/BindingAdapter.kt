package org.sjhstudio.lostark.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import org.sjhstudio.lostark.R

@BindingAdapter("imageFromUrl")
fun ImageView.bindImageFromUrl(url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(context)
            .load(url)
            .into(this)
    } else {
        setImageResource(0)
    }
}

@BindingAdapter("arrowIcon")
fun ImageView.bindArrowIcon(collapse: Boolean) {
    setImageResource(if (collapse) R.drawable.ic_down_arrow else R.drawable.ic_up_arrow)
}