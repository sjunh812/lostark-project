package org.sjhstudio.lostark.data.di

import dagger.Binds
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sjhstudio.lostark.data.repository.CharacterInfoRepositoryImpl
import org.sjhstudio.lostark.domain.repository.CharacterInfoRepository
import javax.inject.Singleton

@dagger.Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCharacterInfoRepository(characterInfoRepositoryImpl: CharacterInfoRepositoryImpl): CharacterInfoRepository
}