package org.sjhstudio.lostark.util

import android.annotation.SuppressLint
import android.media.audiofx.DynamicsProcessing.Eq
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup
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

// 장비, 악세 이미지 초기화
fun initEquipmentImage(imageViews: List<ImageView>) {
    imageViews.forEach { imageView ->
        imageView.setImageResource(0)
        imageView.background =
            ContextCompat.getDrawable(imageView.context, R.drawable.bg_equipment_default)
    }
}

// 장비, 악세 품질 초기화
fun initEquipmentQuality(textViews: List<TextView>) {
    textViews.forEach { textView ->
        textView.text = textView.context.getString(R.string.label_default_quality)
        textView.background =
            ContextCompat.getDrawable(textView.context, R.drawable.bg_equipment_quality_7)
    }
}

// 장비, 악세 TextView 초기화
fun initEquipmentTextView(textViews: List<TextView>) {
    textViews.forEach { textView ->
        textView.text = ""
    }
}

// 장비

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
        100 -> ContextCompat.getDrawable(context, R.drawable.bg_equipment_quality_1)
        in 90..99 -> ContextCompat.getDrawable(context, R.drawable.bg_equipment_quality_2)
        in 70..89 -> ContextCompat.getDrawable(context, R.drawable.bg_equipment_quality_3)
        in 30..69 -> ContextCompat.getDrawable(context, R.drawable.bg_equipment_quality_4)
        in 10..29 -> ContextCompat.getDrawable(context, R.drawable.bg_equipment_quality_5)
        in 1..9 -> ContextCompat.getDrawable(context, R.drawable.bg_equipment_quality_6)
        else -> ContextCompat.getDrawable(context, R.drawable.bg_equipment_quality_7)
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

fun TextView.setAccessoryEffectList(equipment: Equipment) {
    equipment.effects?.let { list ->
        isVisible = true
        var str = ""

        list.forEachIndexed { i, effect ->
            if (!effect.isSpecial) {
                str += "${effect.name} ${effect.value}"
                if (i != list.lastIndex) str += "  "
            }
        }

        text = str
    }
}

@SuppressLint("SetTextI18n")
fun ChipGroup.setAccessoryEngravingList(equipment: Equipment) {
    removeAllViews()
    equipment.engravings?.let { list ->
        list.forEach { engraving ->
            val chip: Chip = Chip(context).apply {
                val drawable = ChipDrawable.createFromAttributes(
                    context,
                    null,
                    0,
                    R.style.Widget_LostArk_Chip
                )
                setChipDrawable(drawable)
                setTextAppearanceResource(R.style.TextAppearance_LostArk_Chip)
                text = "${engraving.name} ${engraving.active}"
                isCheckable = false
                isClickable = false
            }
            addView(chip)
        }
    }
}

fun getBraceletSpecialEffects(equipment: Equipment) : List<Equipment.Effect> {
    val list = arrayListOf<Equipment.Effect>()

    equipment.effects?.forEach { effect ->
        if (effect.isSpecial) list.add(effect)
    }

    return list
}