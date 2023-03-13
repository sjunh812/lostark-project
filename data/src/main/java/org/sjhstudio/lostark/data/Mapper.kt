package org.sjhstudio.lostark.data

import org.sjhstudio.lostark.data.model.armory.*
import org.sjhstudio.lostark.domain.model.response.*

// 프로필(Profile) 매핑
internal fun mapperToProfile(profileDto: ProfileDto) =
    Profile(
        characterImageUrl = profileDto.characterImage ?: "",
        expeditionLevel = profileDto.expeditionLevel.toString(),
        pvpGradeName = profileDto.pvpGradeName,
        townLevel = profileDto.townLevel.toString(),
        townName = profileDto.townName,
        title = profileDto.title ?: "",
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

// 각인(Engraving) 매핑
internal fun mapperToEngraving(engravingDto: EngravingDto) =
    Engraving(
        slots = engravingDto.engravings?.map { slotDto ->
            Engraving.Slot(
                index = slotDto.slot,
                name = slotDto.name,
                iconUrl = slotDto.icon,
                tooltip = slotDto.tooltip
            )
        },
        effects = engravingDto.effects?.map { effectDto ->
            Engraving.Effect(
                name = effectDto.name,
                description = effectDto.description
            )
        }
    )

/**
 * @description : 장비 매핑
 * @return : HashMap<String, Equipment>
 *           (Key : 장비 타입, Value : 장비 객체)
 */
internal fun mapperToEquipmentMap(dtoList: List<EquipmentDto>): HashMap<String, Equipment> {
    val map = hashMapOf<String, Equipment>()

    dtoList.forEach { dto ->
        var type = dto.type
        val level = mapperToEquipmentLevel(dto.type, dto.name)
        val setInfo = mapperToEquipmentSet(dto.tooltip, dto.name)
        val engravingList = mapperToAccessoryEngravingList(dto.type, dto.tooltip)
        val effectList = mapperToAccessoryEffectList(dto.type, dto.tooltip)

        if (map.containsKey(dto.type)) type = "${dto.type}2"

        map[type] = Equipment(
            type = dto.type,
            name = dto.name,
            iconUrl = dto.icon,
            grade = dto.grade,
            quality = mapperToEquipmentQuality(dto.type, dto.tooltip, engravingList, effectList),
            level = level,
            setName = setInfo?.get(0) ?: "",
            setLevel = setInfo?.get(1) ?: "",
            summary = mapperToEquipmentSummary(dto.type, level),
            engravings = engravingList,
            effects = effectList
        )
    }

    return map
}

// 보석(Gem) 매핑
internal fun mapperToGem(dto: GemDto): Gem {
    val gems = mutableListOf<Gem.GemInfo>()

    dto.gems.forEach { gemDto ->
        val priority =
            if (gemDto.name.contains("멸화")) 0
            else if (gemDto.name.contains("홍염")) 1
            else if (gemDto.name.contains("청명")) 2
            else if (gemDto.name.contains("원해")) 3
            else 4
        val gemInfo = Gem.GemInfo(
            priority = priority,
            slot = gemDto.slot.toString(),
            name = gemDto.name,
            iconUrl = gemDto.icon,
            level = gemDto.level,
            grade = gemDto.grade
        )
        gems.add(gemInfo)
    }
    dto.effects.forEach { effectDto ->
        val effect = Gem.GemInfo.Effect(
            gemSlot = effectDto.gemSlot.toString(),
            name = effectDto.name,
            description = effectDto.description,
            iconUrl = effectDto.icon
        )
        gems.forEach { gem ->
            if (gem.slot.toIntOrNull() == effectDto.gemSlot) {
                gem.effect = effect
            }
        }
    }
    gems.sortWith { gem1, gem2 ->
        if (gem1.priority < gem2.priority) -1
        else if (gem1.priority > gem2.priority) 1
        else {
            if (gem1.level < gem2.level) 1
            else -1
        }
    }

    return Gem(gems = gems)
}

// 카드(Card) 매핑
internal fun mapperToCard(dto: CardDto) =
    Card(
        cards = dto.cards.map { card ->
            CardInfo(
                slot = card.slot,
                name = card.name,
                iconUrl = card.icon,
                awakeCount = card.awakeCount,
                awakeTotal = card.awakeTotal,
                grade = card.grade,
                tooltip = card.tooltip
            )
        },
        effects = dto.effects.map { effect ->
            CardEffect(
                index = effect.index,
                slots = effect.cardSlots,
                items = effect.items.map {
                    CardEffect.Item(
                        name = it.name,
                        description = it.description
                    )
                }
            )
        }
    )


/**
 * @description : 장비 품질 매핑
 *                1. 팔찌 : 기본 특성 및 전투 특성을 표시     ex) 치 특 힘
 *                2. 어빌리티 스톤 : 세공 결과를 표시    ex) 9 7 1
 *                3. 그외 장비(악세사리) : 품질 수치를 표시    ex) 100
 */
internal fun mapperToEquipmentQuality(
    type: String,
    tooltip: String,
    engravingList: List<Equipment.Engraving>?,
    effectList: List<Equipment.Effect>?
): String {
    var quality = ""

    when (type) {
        "팔찌" -> {
            effectList?.forEach { effect ->
                if (!effect.isSpecial) quality += "${effect.name[0]} "
            }
        }
        "어빌리티 스톤" -> {
            engravingList?.let { engravings ->
                engravings.forEach { engraving ->
                    quality += "${engraving.active} "
                }
            }
        }
        else -> {
            // ex) qualityValue": 96,
            val startIndexOfWord = tooltip.indexOf("qualityValue")
            val startIndexOfQuality = startIndexOfWord + 15

            for (i in startIndexOfQuality..startIndexOfQuality + 3) {
                if (tooltip[i].isDigit()) quality += tooltip[i]
            }
        }
    }

    return quality.trim()
}

// 장비 레벨 매핑
internal fun mapperToEquipmentLevel(type: String, name: String): String {
    return when (type) {
        "무기", "투구", "어깨", "상의", "하의", "장갑" -> name.split(" ")[0].substring(1)
        else -> "-1"
    }
}

// 장비 세트 매핑
internal fun mapperToEquipmentSet(tooltip: String, name: String): ArrayList<String>? {
    val setName = when {
        name.contains("지배") -> "지배"
        name.contains("배신") -> "배신"
        name.contains("갈망") -> "갈망"
        name.contains("파괴") -> "파괴"
        name.contains("매혹") -> "매혹"
        name.contains("사멸") -> "사멸"
        name.contains("악몽") -> "악몽"
        name.contains("환각") -> "환각"
        name.contains("구원") -> "구원"
        else -> return null
    }
    val levelIndex = tooltip.indexOf(setName, 200) + 28
    val level = tooltip[levelIndex].toString()

    return arrayListOf(setName, level)
}

/**
 * @description : 장비 Summary 매핑
 * @example : 무기 20(이름 레벨)
 */
internal fun mapperToEquipmentSummary(type: String, level: String): String {
    var summary = type
    if (level != "-1") summary += " $level"

    return summary
}

/**
 * @description : 악세사리 각인 매핑
 *                감소 효과 각인의 경우 마지막 원소로 삽입
 */
internal fun mapperToAccessoryEngravingList(
    type: String,
    tooltip: String
): List<Equipment.Engraving>? {
    return when (type) {
        "목걸이", "귀걸이", "반지", "어빌리티 스톤" -> {
            val list = arrayListOf<Equipment.Engraving>()
            var index = tooltip.indexOf("[<FONT COLOR='#FFFFAC'>")

            while (index != -1) {
                var i = index + 23
                var name = ""
                var active = ""
                var nameFlag = true

                while (tooltip[i] != '}') {
                    if (tooltip[i] == '<') {
                        if (nameFlag) nameFlag = false
                        else break
                    }
                    if (nameFlag) name += tooltip[i]
                    if (tooltip[i].isDigit()) active += tooltip[i]
                    i++
                }

                list.add(Equipment.Engraving(name, active))
                index =
                    if (list.size >= 2) tooltip.indexOf("[<FONT COLOR='#FE2E2E'>", i)
                    else tooltip.indexOf("[<FONT COLOR='#FFFFAC'>", i)
            }

            list
        }
        else -> null
    }
}

/**
 * @description : 악세사리 특성 매핑
 *                1. 기본 특성 : `isSpecial` = false    ex) 특화, 치명, 힘, 체력..
 *                2. 특수 효과 : `isSpecial` = true 이며, 팔찌에 한한 특성     ex) 정밀, 순환, 망치..
 */
internal fun mapperToAccessoryEffectList(
    type: String,
    tooltip: String
): List<Equipment.Effect>? {
    val effectList = arrayListOf<Equipment.Effect>()
    var index = tooltip.indexOf("Element_001")

    return when (type) {
        "팔찌" -> {
            index = tooltip.indexOf("</img>")

            while (index != -1) {
                var i = index + 6
                var str = ""

                index = tooltip.indexOf("</img>", index + 1)

                while (!str.contains("<img") && !str.contains("\"\r\n")) {
                    str += tooltip[i++]
                }
                str = str.replace("\"\r\n", "").trim()

                var name: String = ""   // 효과
                var value: String = ""
                var special: Boolean = false

                if (str.startsWith("[")) {
                    val end = str.indexOf("]")
                    name = str.substring(1, end)
                    value = str.substring(end + 2).replace("<BR><img", "")
                    special = true
                } else {
                    if (str.contains("효과 부여 가능")) continue

                    val words = str.split(" ")
                    words.forEachIndexed { idx, word ->
                        if (idx != words.lastIndex) {
                            name += word
                        } else {
                            val valueCandidate = word.substring(1)
                            for (j in valueCandidate.indices) {
                                if (valueCandidate[j].isDigit()) value += valueCandidate[j]
                            }
                        }
                    }
                    special = false
                }

                effectList.add(Equipment.Effect(name, value, special))
                println("xxx 팔찌 : $effectList")
            }

            effectList
        }
        "목걸이", "귀걸이", "반지", "어빌리티 스톤" -> {
            var start = 0
            val last = if (type == "어빌리티 스톤") 1 else 2

            while (index != -1 || start <= last) {
                if (start++ == last) {
                    var i = index + 15
                    var str = ""


                    while (tooltip[i] != '"') {
                        str += tooltip[i++]
                    }

                    str.split("<BR>").forEach { effect ->
                        effectList.add(
                            Equipment.Effect(
                                name = effect.split(" +")[0],
                                value = effect.split(" +")[1]
                            )
                        )
                    }
                }

                index = tooltip.indexOf("Element_001", index + 1)
            }

            effectList
        }
        else -> null
    }
}