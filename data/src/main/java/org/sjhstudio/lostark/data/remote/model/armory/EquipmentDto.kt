package org.sjhstudio.lostark.data.remote.model.armory

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class EquipmentDto(
    @Json(name = "Type") val type: String,
    @Json(name = "Name") val name: String,
    @Json(name = "Icon") val icon: String,
    @Json(name = "Grade") val grade: String,
    @Json(name = "Tooltip") val tooltip: String
)