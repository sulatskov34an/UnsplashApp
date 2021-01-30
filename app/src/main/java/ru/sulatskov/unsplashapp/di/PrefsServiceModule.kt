package ru.sulatskov.unsplashapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.sulatskov.unsplashapp.model.prefs.PrefsService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PrefsServiceModule {

    @Provides
    @Singleton
    fun providesPrefsService(context: Context): PrefsService {
        return PrefsService(context)
    }
}