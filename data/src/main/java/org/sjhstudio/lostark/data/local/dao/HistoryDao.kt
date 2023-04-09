package org.sjhstudio.lostark.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import org.sjhstudio.lostark.data.local.model.HistoryEntity

@Dao
interface HistoryDao {

    @Query("SELECT * FROM historyEntity LIMIT 10")
    fun getHistoryList(): Flow<List<HistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: HistoryEntity)

    @Delete
    suspend fun deleteHistory(history: HistoryEntity)
}