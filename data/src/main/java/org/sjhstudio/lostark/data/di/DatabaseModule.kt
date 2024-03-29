package org.sjhstudio.lostark.data.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sjhstudio.lostark.data.local.AppDatabase
import javax.inject.Singleton

@dagger.Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    private const val DB_NAME = "lostArk.db"

    @Provides
    @Singleton
    fun provideDatabase(application: Application): AppDatabase {
        synchronized(this) {
            return Room.databaseBuilder(
                application,
                AppDatabase::class.java,
                DB_NAME
            ).build()
        }
    }
}