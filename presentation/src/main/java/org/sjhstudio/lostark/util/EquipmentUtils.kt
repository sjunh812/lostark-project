package org.sjhstudio.lostark.util

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import org.sjhstudio.lostark.R
import org.sjhstudio.lostark.domain.model.response.Equipment

data class EquipmentSet(
    var setName: String,
    var count: Int
) : Comparable<EquipmentSet> {

    override fun compareTo(other: EquipmentSet): Int {
        if (this.setName != other.setName) return this.count.compareTo(other.count)
        return this.setName.compareTo(other.setName)
    }
}

fun ImageView.setEquipmentImage(equipment: Equipment) {
    background = when (equipment.grade) {
        "고대" -> ContextCompat.getDrawable(context, R.drawable.bg_equipment_ancient)
        "유물" -> ContextCompat.getDrawable(context, R.drawable.bg_equipment_relic)
        "전설" -> ContextCompat.getDrawable(context, R.drawable.bg_equipment_legend)
        else -> ContextCompat.getDrawable(context, R.drawable.bg_equipment_default)
    }
    Glide.with(context)
        .load(equipment.iconUrl)
        .into(this)
}

fun TextView.setEquipmentQuality(equipment: Equipment) {
    text = equipment.quality
    background = when (equipment.quality.toIntOrNull()) {
        100 -> ContextCompat.getDrawable(context, R.drawable.bg_equiment_quality_1)
        in 90..99 -> ContextCompat.getDrawable(context, R.drawable.bg_equiment_quality_2)
        in 70..89 -> ContextCompat.getDrawable(context, R.drawable.bg_equiment_quality_3)
        in 30..69 -> ContextCompat.getDrawable(context, R.drawable.bg_equiment_quality_4)
        in 10..29 -> ContextCompat.getDrawable(context, R.drawable.bg_equiment_quality_5)
        in 1..9 -> ContextCompat.getDrawable(context, R.drawable.bg_equiment_quality_6)
        else -> ContextCompat.getDrawable(context, R.drawable.bg_equiment_quality_7)
    }
}

fun TextView.setEquipmentSummary(equipment: Equipment) {
    text = equipment.summary
}

@SuppressLint("SetTextI18n")
fun TextView.setEquipmentSet(equipment: Equipment) {
    if (equipment.setName.isNotEmpty()) {
        isVisible = true
        text = "${equipment.setName} Lv.${equipment.setLevel}"
    } else {
        isVisible = false
    }
}

fun setEquipmentSetList(list: MutableList<EquipmentSet>, equipment: Equipment) {
    list.takeIf { it.isNotEmpty() }?.forEach { set ->
        if (set.setName == equipment.setName) {
            set.count++
            return
        }
    }

    list.add(EquipmentSet(equipment.setName, 1))
}

fun getEquipmentSetSummary(list: MutableList<EquipmentSet>): String {
    var summary = ""

    list.takeIf { it.isNotEmpty() }?.forEach { set ->
        summary += "${set.setName} ${set.count} "
    }

    return summary.trim()
}