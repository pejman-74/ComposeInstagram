package com.composeinstagram.di

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import javax.inject.Named
import javax.inject.Singleton
@OptIn(ExperimentalCoroutinesApi::class)
@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DispatcherModule::class]
)
object TestDispatcherModule {


    @Provides
    @Singleton
    @Named("TestDispatcher")
    fun provideTestDispatcher(): TestCoroutineDispatcher = TestCoroutineDispatcher()

    @Provides
    @Singleton
    @Named("MainDispatcher")
    fun provideMainDispatcher(
        @Named("TestDispatcher")
        testCoroutineDispatcher: TestCoroutineDispatcher
    ): CoroutineDispatcher = testCoroutineDispatcher


    @Provides
    @Singleton
    @Named("IODispatcher")
    fun provideIODispatcher(
        @Named("TestDispatcher")
        testCoroutineDispatcher: TestCoroutineDispatcher
    ): CoroutineDispatcher = testCoroutineDispatcher
}