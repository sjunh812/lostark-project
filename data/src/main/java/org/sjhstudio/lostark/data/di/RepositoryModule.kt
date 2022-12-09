package org.sjhstudio.lostark.data.di

import dagger.Binds
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sjhstudio.lostark.data.repository.ArmoryRepositoryImpl
import org.sjhstudio.lostark.data.repository.CharacterInfoRepositoryImpl
import org.sjhstudio.lostark.data.source.ArmoryDataSource
import org.sjhstudio.lostark.domain.repository.ArmoryRepository
import org.sjhstudio.lostark.domain.repository.CharacterInfoRepository
import javax.inject.Singleton

@dagger.Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCharacterInfoRepository(characterInfoRepositoryImpl: CharacterInfoRepositoryImpl): CharacterInfoRepository

    @Binds
    @Singleton
    abstract fun bindArmoryRepository(armoryRepositoryImpl: ArmoryRepositoryImpl): ArmoryRepository
}