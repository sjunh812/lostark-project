package org.sjhstudio.lostark.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import org.sjhstudio.lostark.data.local.dao.HistoryDao
import org.sjhstudio.lostark.data.local.model.HistoryEntity

@Database(entities = [HistoryEntity::class], version = 1)
internal abstract class AppDatabase : RoomDatabase() {

    abstract fun historyDao(): HistoryDao
}