package org.sjhstudio.lostark.data.model.armory

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class GemDto(
    @Json(name = "Gems") val gems: List<Gem>,
    @Json(name = "Effects") val effects: List<Effect>
) {

    @JsonClass(generateAdapter = true)
    internal data class Gem(
        @Json(name = "Slot") val slot: Int,
        @Json(name = "Name") val name: String,
        @Json(name = "Icon") val icon: String,
        @Json(name = "Level") val level: Int,
        @Json(name = "Grade") val grade: String,
        @Json(name = "Tooltip") val tooltip: String
    )

    @JsonClass(generateAdapter = true)
    internal data class Effect(
        @Json(name = "GemSlot") val gemSlot: Int,
        @Json(name = "Name") val name: String,
        @Json(name = "Description") val description: String,
        @Json(name = "Icon") val icon: String,
        @Json(name = "Tooltip") val tooltip: String
    )
}