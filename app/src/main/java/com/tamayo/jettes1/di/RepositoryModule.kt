package com.tamayo.jettes1.di

import com.tamayo.jettes1.data.MyRepository
import com.tamayo.jettes1.data.MyRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(repositoryImpl: MyRepositoryImpl): MyRepository
}