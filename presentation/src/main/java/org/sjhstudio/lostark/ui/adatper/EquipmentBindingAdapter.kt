package org.sjhstudio.lostark.ui.adatper

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import org.sjhstudio.lostark.R
import org.sjhstudio.lostark.domain.model.response.Equipment

@BindingAdapter("equipmentImage")
fun ImageView.bindEquipmentImage(equipment: Equipment?) {
    equipment?.let { data ->
        background = when(data.grade) {
            "고대" -> ContextCompat.getDrawable(context, R.drawable.bg_equipment_ancient)
            "유물" -> ContextCompat.getDrawable(context, R.drawable.bg_equipment_relic)
            "전설" -> ContextCompat.getDrawable(context, R.drawable.bg_equipment_legend)
            else -> ContextCompat.getDrawable(context, R.drawable.bg_equipment_default)
        }

        Glide.with(context)
            .load(data.iconUrl)
            .into(this)
    }
}

@BindingAdapter("equipmentQuality")
fun TextView.bindEquipmentQuality(equipment: Equipment?) {
    equipment?.let { data ->
        val quality = data.quality.toInt()
        background = when (quality) {
            100 -> ContextCompat.getDrawable(context, R.drawable.bg_equiment_quality_1)
            in 90..99 -> ContextCompat.getDrawable(context, R.drawable.bg_equiment_quality_2)
            in 70..89 -> ContextCompat.getDrawable(context, R.drawable.bg_equiment_quality_3)
            in 30..69 -> ContextCompat.getDrawable(context, R.drawable.bg_equiment_quality_4)
            in 10..29 -> ContextCompat.getDrawable(context, R.drawable.bg_equiment_quality_5)
            in 1..9 -> ContextCompat.getDrawable(context, R.drawable.bg_equiment_quality_6)
            else -> ContextCompat.getDrawable(context, R.drawable.bg_equiment_quality_7)
        }
    }
}