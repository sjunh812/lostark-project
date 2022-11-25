package org.sjhstudio.lostark.data.di

import dagger.Binds
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sjhstudio.lostark.data.source.CharacterInfoDataSource
import org.sjhstudio.lostark.data.source.CharacterInfoDataSourceImpl
import javax.inject.Singleton

@dagger.Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindCharacterInfoDataSource(characterInfoDataSourceImpl: CharacterInfoDataSourceImpl): CharacterInfoDataSource
}