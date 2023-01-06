package org.sjhstudio.lostark.data.model.armory

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class ProfileDto(
    @Json(name = "CharacterImage") val characterImage: String,
    @Json(name = "ExpeditionLevel") val expeditionLevel: Int,
    @Json(name = "PvpGradeName") val pvpGradeName: String,
    @Json(name = "TownLevel") val townLevel: Int,
    @Json(name = "TownName") val townName: String,
    @Json(name = "Title") val title: String?,
    @Json(name = "GuildMemberGrade") val guildMemberGrade: String?,
    @Json(name = "GuildName") val guildName: String?,
    @Json(name = "Stats") val stats: List<Stat>,
    @Json(name = "Tendencies") val tendencies: List<Tendency>,
    @Json(name = "ServerName") val serverName: String,
    @Json(name = "CharacterName") val characterName: String,
    @Json(name = "CharacterLevel") val characterLevel: Int,
    @Json(name = "CharacterClassName") val characterClassName: String,
    @Json(name = "ItemAvgLevel") val itemAvgLevel: String,
    @Json(name = "ItemMaxLevel") val itemMaxLevel: String
) {

    @JsonClass(generateAdapter = true)
    internal data class Stat(
        @Json(name = "Type") val type: String,
        @Json(name = "Value") val value: String,
        @Json(name = "Tooltip") val tooltip: List<String>
    )

    @JsonClass(generateAdapter = true)
    internal data class Tendency(
        @Json(name = "Type") val type: String,
        @Json(name = "Point") val point: Int,
        @Json(name = "MaxPoint") val maxPoint: Int
    )
}