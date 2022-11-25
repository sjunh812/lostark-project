package org.sjhstudio.lostark.data

import org.sjhstudio.lostark.data.model.CharacterInfoDto
import org.sjhstudio.lostark.domain.model.*

internal fun mapperToUserInfo(dto: CharacterInfoDto): CharacterInfo {
    return CharacterInfo(
        avatarImgUrl = dto.avatarImgUrl,
        basic = dto.basic?.let { basic ->
            Basic(
                classInfo = Basic.Class(
                    iconUrl = basic.classInfo.iconUrl,
                    name = basic.classInfo.name
                ),
                engrave = basic.engrave,
                guild = basic.guild,
                levelInfo = Basic.Level(
                    battle = basic.levelInfo.battle,
                    expedition = basic.levelInfo.expedition,
                    item = basic.levelInfo.item
                ),
                name = basic.name,
                server = basic.server,
                stat = Basic.Stat(
                    agility = basic.stat.agility,
                    attack = basic.stat.attack,
                    critical = basic.stat.critical,
                    endurance = basic.stat.endurance,
                    health = basic.stat.health,
                    proficiency = basic.stat.proficiency,
                    specialty = basic.stat.specialty,
                    subdue = basic.stat.subdue
                ),
                tendency = Basic.Tendency(
                    bravery = basic.tendency.bravery,
                    charm = basic.tendency.charm,
                    intellect = basic.tendency.intellect,
                    kindness = basic.tendency.kindness
                ),
                title = basic.title,
                wisdom = Basic.Wisdom(
                    level = basic.wisdom.level,
                    name = basic.wisdom.name
                )
            )
        },
        card = dto.card?.map { cardInfo ->
            CardInfo(
                effect = cardInfo.effect,
                name = cardInfo.name
            )
        },
        characterList = dto.characterList?.map { character ->
            Character(
                classInfo = character.classInfo,
                level = character.level,
                name = character.name,
                server = character.server
            )
        },
        collections = dto.collections,
        detailedTri = dto.detailedTri?.mapValues { entry ->
            entry.value.map { triInfo ->
                TriInfo(
                    level = triInfo.level,
                    name = triInfo.name
                )
            }
        },
        gold = dto.gold?.let { gold ->
            Gold(
                goldList = gold.goldList.map { goldInfo ->
                    Gold.GoldInfo(
                        classInfo = goldInfo.classInfo,
                        gold = goldInfo.gold,
                        level = goldInfo.level,
                        name = goldInfo.name
                    )
                },
                totalGold = gold.totalGold
            )
        },
        items = dto.items?.let { items ->
            Items(
                headArmor = items.headArmor?.let { armor ->
                    Items.Armor(armor.name, armor.quality)
                },
                shoulderArmor = items.headArmor?.let { armor ->
                    Items.Armor(armor.name, armor.quality)
                },
                top = items.headArmor?.let { armor ->
                    Items.Armor(armor.name, armor.quality)
                },
                pants = items.headArmor?.let { armor ->
                    Items.Armor(armor.name, armor.quality)
                },
                glove = items.headArmor?.let { armor ->
                    Items.Armor(armor.name, armor.quality)
                },
                weapon = items.headArmor?.let { armor ->
                    Items.Armor(armor.name, armor.quality)
                },
                necklace = items.necklace?.let { necklace ->
                    Items.Accessory(
                        engrave = necklace.engrave.map { engraveInfo ->
                            Items.EngraveInfo(engraveInfo.effect, engraveInfo.engraveName)
                        },
                        name = necklace.name,
                        plus = necklace.plus,
                        quality = necklace.quality
                    )
                },
                earring1 = items.earring1?.let { earring ->
                    Items.Accessory(
                        engrave = earring.engrave.map { engraveInfo ->
                            Items.EngraveInfo(engraveInfo.effect, engraveInfo.engraveName)
                        },
                        name = earring.name,
                        plus = earring.plus,
                        quality = earring.quality
                    )
                },
                earring2 = items.earring2?.let { earring ->
                    Items.Accessory(
                        engrave = earring.engrave.map { engraveInfo ->
                            Items.EngraveInfo(engraveInfo.effect, engraveInfo.engraveName)
                        },
                        name = earring.name,
                        plus = earring.plus,
                        quality = earring.quality
                    )
                },
                ring1 = items.ring1?.let { ring ->
                    Items.Accessory(
                        engrave = ring.engrave.map { engraveInfo ->
                            Items.EngraveInfo(engraveInfo.effect, engraveInfo.engraveName)
                        },
                        name = ring.name,
                        plus = ring.plus,
                        quality = ring.quality
                    )
                },
                ring2 = items.ring2?.let { ring ->
                    Items.Accessory(
                        engrave = ring.engrave.map { engraveInfo ->
                            Items.EngraveInfo(engraveInfo.effect, engraveInfo.engraveName)
                        },
                        name = ring.name,
                        plus = ring.plus,
                        quality = ring.quality
                    )
                },
                bracelet = items.bracelet?.let { bracelet ->
                    Items.Bracelet(bracelet.name, bracelet.plus)
                },
                abilityStone = items.abilityStone?.let { abilityStone ->
                    Items.AbilityStone(
                        basic = abilityStone.basic,
                        engrave = abilityStone.engrave.map { engraveInfo ->
                            Items.EngraveInfo(engraveInfo.effect, engraveInfo.engraveName)
                        },
                        name = abilityStone.name,
                        plus = abilityStone.plus
                    )
                }
            )
        },
        jewl = dto.jewl?.map { jewlInfo ->
            JewlInfo(
                effect = jewlInfo.effect,
                jewlName = jewlInfo.jewlName,
                skillName = jewlInfo.skillName
            )
        },
        skill = dto.skill?.let { skill ->
            Skill(
                skillList = skill.skillList.map { skillInfo ->
                    Skill.SkillInfo(
                        level = skillInfo.level,
                        name = skillInfo.name,
                        tri = skillInfo.tri
                    )
                },
                skillPoint = Skill.SkillPoint(
                    total = skill.skillPoint.total,
                    used = skill.skillPoint.used
                ),
                skillRune = skill.skillRune
            )
        }
    )
}