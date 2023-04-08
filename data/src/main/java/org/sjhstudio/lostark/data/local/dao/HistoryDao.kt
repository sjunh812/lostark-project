package org.sjhstudio.lostark.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.sjhstudio.lostark.data.local.model.HistoryEntity

@Dao
interface HistoryDao {

    @Query("SELECT * FROM history LIMIT 10")
    fun getHistoryList(): Flow<List<HistoryEntity>>

    @Insert
    fun insertHistory(history: HistoryEntity)

    @Delete
    fun deleteHistory(history: HistoryEntity)
}