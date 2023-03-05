package com.testpois.domain.di

import com.testpois.domain.extensions.DefaultDispatcherProvider
import com.testpois.domain.extensions.DispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface CommonDomainModule {

    @Binds
    @Singleton
    fun bindDispatcherProvider(defaultDispatcherProvider: DefaultDispatcherProvider): DispatcherProvider
}
