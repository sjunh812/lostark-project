package org.sjhstudio.lostark.domain.model.response

data class Engraving(
    val slots: List<Slot>?,
    val effects: List<Effect>?
) {

    data class Slot(
        val index: Int,
        val name: String,
        val iconUrl: String,
        val tooltip: String
    )

    data class Effect(
        val name: String,
        val description: String,
        var isExpanded: Boolean = false
    )
}