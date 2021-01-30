package ru.sulatskov.unsplashapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ContextModule() {

    @Provides
    @Singleton
    internal fun providesContext(@ApplicationContext context: Context): Context {
        return context
    }
}