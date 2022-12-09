package org.sjhstudio.lostark.data.di

import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sjhstudio.lostark.data.api.ArmoryService
import org.sjhstudio.lostark.data.api.LostArkService
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@dagger.Module
@InstallIn(SingletonComponent::class)
internal object ApiModule {

    @Provides
    @Singleton
    fun provideLostArkService(retrofit: Retrofit): LostArkService =
        retrofit.create(LostArkService::class.java)

    @Provides
    @Singleton
    fun provideArmoryService(retrofit: Retrofit): ArmoryService =
        retrofit.create(ArmoryService::class.java)
}