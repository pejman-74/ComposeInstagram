package com.composeinstagram.di

import com.composeinstagram.helper.FakeIGClientHelper
import com.composeinstagram.helper.IGClientHelperInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [IGClientModule::class])
object TestIGClientModule {

    @Singleton
    @Provides
    fun provideFakeIGClientHelper(): IGClientHelperInterface = FakeIGClientHelper()
}