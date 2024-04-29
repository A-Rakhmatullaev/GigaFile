package com.example.gigafile.di.modules

import com.example.gigafile.data.repositories.TestRepositoryImpl
import com.example.gigafile.domain.repositories.TestRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideTestRepository(): TestRepository = TestRepositoryImpl()
}