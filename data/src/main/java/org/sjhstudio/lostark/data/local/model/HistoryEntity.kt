package org.sjhstudio.lostark.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.sjhstudio.lostark.domain.model.response.History

@Entity(tableName = "historyEntity")
data class HistoryEntity(
    @PrimaryKey val name: String,
    val level: Int,
    val className: String
) {

    fun toHistory() = History(name, level.toString(), className)
}