package org.sjhstudio.lostark.util

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import org.sjhstudio.lostark.R
import org.sjhstudio.lostark.domain.model.response.Equipment

fun ImageView.setEquipmentBackground(grade: String) {
    background = when (grade) {
        "고대" -> ContextCompat.getDrawable(context, R.drawable.bg_equipment_ancient)
        "유물" -> ContextCompat.getDrawable(context, R.drawable.bg_equipment_relic)
        "전설" -> ContextCompat.getDrawable(context, R.drawable.bg_equipment_legend)
        "영웅" -> ContextCompat.getDrawable(context, R.drawable.bg_equipment_hero)
        "희귀" -> ContextCompat.getDrawable(context, R.drawable.bg_equipment_rare)
        "고급" -> ContextCompat.getDrawable(context, R.drawable.bg_equipment_high)
        else -> ContextCompat.getDrawable(context, R.drawable.bg_equipment_default)
    }
}

fun TextView.setEquipmentSetSummary(equipmentMap: HashMap<String, Equipment>) {
    val setList = arrayListOf<EquipmentSet>()
    var setSummary = "" // 지배 4 악몽 2

    println("xxx $equipmentMap")

    for (equipment in equipmentMap.values) {
        if (equipment.type == "무기" || equipment.type == "투구" || equipment.type == "상의" || equipment.type == "하의" || equipment.type == "장갑" || equipment.type == "어깨") {
            var matchCount = 0

            setList.forEach { set ->
                if (set.setName == equipment.setName) {
                    set.count++
                    matchCount++
                }
            }

            if (matchCount == 0) setList.add(EquipmentSet(equipment.setName, 1))
        }
    }

    setList.forEach { set ->
        setSummary += "${set.setName} ${set.count} "
    }

    isVisible = setSummary.isNotEmpty()
    text = setSummary.trim()
}

data class EquipmentSet(
    var setName: String,
    var count: Int
) : Comparable<EquipmentSet> {

    override fun compareTo(other: EquipmentSet): Int {
        if (this.setName != other.setName) return this.count.compareTo(other.count)
        return this.setName.compareTo(other.setName)
    }
}
