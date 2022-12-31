package org.sjhstudio.lostark.data

import org.sjhstudio.lostark.data.model.armory.EngravingDto
import org.sjhstudio.lostark.data.model.armory.EquipmentDto
import org.sjhstudio.lostark.data.model.armory.ProfileDto
import org.sjhstudio.lostark.domain.model.response.Engraving
import org.sjhstudio.lostark.domain.model.response.Equipment
import org.sjhstudio.lostark.domain.model.response.Profile
import kotlin.math.exp

internal fun mapperToProfile(profileDto: ProfileDto) =
    Profile(
        characterImageUrl = profileDto.characterImage,
        expeditionLevel = profileDto.expeditionLevel.toString(),
        pvpGradeName = profileDto.pvpGradeName,
        townLevel = profileDto.townLevel.toString(),
        townName = profileDto.townName,
        title = profileDto.title,
        guildMemberGrade = profileDto.guildMemberGrade ?: "",
        guildName = profileDto.guildName ?: "",
        stats = profileDto.stats.map { statDto ->
            Profile.Stat(
                type = statDto.type,
                value = statDto.value,
                tooltip = statDto.tooltip
            )
        },
        tendencies = profileDto.tendencies.map { tendencyDto ->
            Profile.Tendency(
                type = tendencyDto.type,
                point = tendencyDto.point.toString(),
                maxPoint = tendencyDto.maxPoint.toString()
            )
        },
        serverName = profileDto.serverName,
        characterName = profileDto.characterName,
        characterLevel = profileDto.characterLevel.toString(),
        characterClassName = profileDto.characterClassName,
        itemLevel = profileDto.itemAvgLevel
    )

internal fun mapperToEngraving(engravingDto: EngravingDto) =
    Engraving(
        slots = engravingDto.engravings.map { slotDto ->
            Engraving.Slot(
                index = slotDto.slot,
                name = slotDto.name,
                iconUrl = slotDto.icon,
                tooltip = slotDto.tooltip
            )
        },
        effects = engravingDto.effects.map { effectDto ->
            Engraving.Effect(
                name = effectDto.name,
                description = effectDto.description
            )
        }
    )

internal fun mapperToEquipmentList(dtoList: List<EquipmentDto>) =
    dtoList.map { dto ->
        val level = mapperToEquipmentLevel(dto.type, dto.name)
        val setInfo = mapperToEquipmentSet(dto.tooltip)
        Equipment(
            type = dto.type,
            name = dto.name,
            iconUrl = dto.icon,
            grade = dto.grade,
            quality = mapperToEquipmentQuality(dto.tooltip),
            level = level,
            setName = setInfo?.get(0) ?: "",
            setLevel = setInfo?.get(1) ?: "",
            summary = mapperToEquipmentSummary(dto.type, level)
        )
    }

internal fun mapperToEquipmentQuality(tooltip: String): String {
    // ex) qualityValue": 96,
    val startIndexOfWord = tooltip.indexOf("qualityValue")
    val startIndexOfQuality = startIndexOfWord + 15
    val expectQuality = tooltip.substring(startIndexOfQuality, startIndexOfQuality + 3)

    return if (expectQuality.last() == ',') expectQuality.substring(0, expectQuality.lastIndex)
    else expectQuality
}

internal fun mapperToEquipmentLevel(type: String, name: String): String {
    return when (type) {
        "무기", "투구", "어깨", "상의", "하의", "장갑" -> name.split(" ")[0].substring(1)
         else -> "-1"
    }
}

internal fun mapperToEquipmentSet(tooltip: String): ArrayList<String>? {
    val set = arrayListOf<String>()
    val startIndexOfWord = tooltip.indexOf("Lv.")
    val startIndexOfSetName = startIndexOfWord - 25

    return try {
        set.add(tooltip.substring(startIndexOfSetName, startIndexOfSetName + 2))
        set.add(tooltip[startIndexOfWord + 3].toString())
        set
    } catch (e: Exception) {
        null
    }
}

internal fun mapperToEquipmentSummary(type: String, level: String): String {
    var summary = type
    if (level != "-1") summary += " $level"
    return summary
}