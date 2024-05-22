package com.example.gigafile.di.modules

import com.example.gigafile.data.repositories.FileSystemRepositoryImpl
import com.example.gigafile.domain.repositories.FileSystemRepository
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
    fun provideTestRepository(): FileSystemRepository = FileSystemRepositoryImpl()
}