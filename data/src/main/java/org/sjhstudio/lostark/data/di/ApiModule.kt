package org.sjhstudio.lostark.data.di

import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sjhstudio.lostark.data.remote.api.ArmoryService
import retrofit2.Retrofit
import javax.inject.Singleton

@dagger.Module
@InstallIn(SingletonComponent::class)
internal object ApiModule {

    @Provides
    @Singleton
    fun provideArmoryService(retrofit: Retrofit): ArmoryService =
        retrofit.create(ArmoryService::class.java)
}