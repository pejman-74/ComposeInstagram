package com.composeinstagram.di

import android.content.Context
import com.composeinstagram.helper.IGClientHelper
import com.composeinstagram.helper.IGClientHelperInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object IGClientModule {

    @Singleton
    @Provides
    fun provideIGClientHelper(@ApplicationContext context: Context): IGClientHelperInterface =
        IGClientHelper(context)
}