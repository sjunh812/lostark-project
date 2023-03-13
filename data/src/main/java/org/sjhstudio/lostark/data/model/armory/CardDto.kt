package org.sjhstudio.lostark.data.model.armory

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CardDto(
    @Json(name = "Cards") val cards: List<Card>,
    @Json(name = "Effects") val effects: List<Effect>
) {

    @JsonClass(generateAdapter = true)
    data class Card(
        @Json(name = "Slot") val slot: Int,
        @Json(name = "Name") val name: String,
        @Json(name = "Icon") val icon: String,
        @Json(name = "AwakeCount") val awakeCount: Int,
        @Json(name = "AwakeTotal") val awakeTotal: Int,
        @Json(name = "Grade") val grade: String,
        @Json(name = "Tooltip") val tooltip: String
    )

    @JsonClass(generateAdapter = true)
    data class Effect(
        @Json(name = "Index") val index: Int,
        @Json(name = "CardSlots") val cardSlots: List<Int>,
        @Json(name = "Items") val items: List<Item>
    ) {

        @JsonClass(generateAdapter = true)
        data class Item(
            @Json(name = "Name") val name: String,
            @Json(name = "Description") val description: String
        )
    }
}