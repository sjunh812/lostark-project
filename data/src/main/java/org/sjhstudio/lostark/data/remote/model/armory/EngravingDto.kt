package org.sjhstudio.lostark.data.remote.model.armory

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class EngravingDto(
    @Json(name = "Engravings") val engravings: List<Engraving>?,
    @Json(name = "Effects") val effects: List<Effect>?
) {

    @JsonClass(generateAdapter = true)
    internal data class Engraving(
        @Json(name = "Slot") val slot: Int,
        @Json(name = "Name") val name: String,
        @Json(name = "Icon") val icon: String,
        @Json(name = "Tooltip") val tooltip: String
    )

    @JsonClass(generateAdapter = true)
    internal data class Effect(
        @Json(name = "Name") val name: String,
        @Json(name = "Description") val description: String
    )
}