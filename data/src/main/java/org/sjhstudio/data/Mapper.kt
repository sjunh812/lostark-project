package org.sjhstudio.data

import org.sjhstudio.data.model.UserInfoDto
import org.sjhstudio.domain.model.*

internal fun mapperToUserInfo(userInfoDto: UserInfoDto): UserInfo {
    return UserInfo(
        avatarImgUrl = userInfoDto.avatarImgUrl,
        basic = userInfoDto.basic?.let { basic ->
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
        card = userInfoDto.card?.map { cardInfo ->
            CardInfo(
                effect = cardInfo.effect,
                name = cardInfo.name
            )
        },
        characterList = userInfoDto.characterList?.map { character ->
            Character(
                classInfo = character.classInfo,
                level = character.level,
                name = character.name,
                server = character.server
            )
        },
        collections = userInfoDto.collections,
        detailedTri = userInfoDto.detailedTri?.mapValues { entry ->
            entry.value.map { triInfo ->
                TriInfo(
                    level = triInfo.level,
                    name = triInfo.name
                )
            }
        },
        gold = userInfoDto.gold?.let { gold ->
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
        items = userInfoDto.items?.let { items ->
            Items(
                headArmor = Items.Armor(items.headArmor.name, items.headArmor.quality),
                shoulderArmor = Items.Armor(items.headArmor.name, items.headArmor.quality),
                top = Items.Armor(items.headArmor.name, items.headArmor.quality),
                pants = Items.Armor(items.headArmor.name, items.headArmor.quality),
                glove = Items.Armor(items.headArmor.name, items.headArmor.quality),
                weapon = Items.Armor(items.headArmor.name, items.headArmor.quality),
                necklace = Items.Accessory(
                    engrave = items.necklace.engrave.map { engraveInfo ->
                        Items.EngraveInfo(engraveInfo.effect, engraveInfo.engraveName)
                    },
                    name = items.necklace.name,
                    plus = items.necklace.plus,
                    quality = items.necklace.quality
                ),
                earring1 = Items.Accessory(
                    engrave = items.earring1.engrave.map { engraveInfo ->
                        Items.EngraveInfo(engraveInfo.effect, engraveInfo.engraveName)
                    },
                    name = items.earring1.name,
                    plus = items.earring1.plus,
                    quality = items.earring1.quality
                ),
                earring2 = Items.Accessory(
                    engrave = items.earring2.engrave.map { engraveInfo ->
                        Items.EngraveInfo(engraveInfo.effect, engraveInfo.engraveName)
                    },
                    name = items.earring2.name,
                    plus = items.earring2.plus,
                    quality = items.earring2.quality
                ),
                ring1 = Items.Accessory(
                    engrave = items.ring1.engrave.map { engraveInfo ->
                        Items.EngraveInfo(engraveInfo.effect, engraveInfo.engraveName)
                    },
                    name = items.ring1.name,
                    plus = items.ring1.plus,
                    quality = items.ring1.quality
                ),
                ring2 = Items.Accessory(
                    engrave = items.ring2.engrave.map { engraveInfo ->
                        Items.EngraveInfo(engraveInfo.effect, engraveInfo.engraveName)
                    },
                    name = items.ring2.name,
                    plus = items.ring2.plus,
                    quality = items.ring2.quality
                ),
                bracelet = Items.Bracelet(items.bracelet.name, items.bracelet.plus),
                abilityStone = Items.AbilityStone(
                    basic = items.abilityStone.basic,
                    engrave = items.abilityStone.engrave.map { engraveInfo ->
                        Items.EngraveInfo(engraveInfo.effect, engraveInfo.engraveName)
                    },
                    name = items.abilityStone.name,
                    plus = items.abilityStone.plus
                )
            )
        },
        jewl = userInfoDto.jewl?.map { jewlInfo ->
            JewlInfo(
                effect = jewlInfo.effect,
                jewlName = jewlInfo.jewlName,
                skillName = jewlInfo.skillName
            )
        },
        skill = userInfoDto.skill?.let { skill ->
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