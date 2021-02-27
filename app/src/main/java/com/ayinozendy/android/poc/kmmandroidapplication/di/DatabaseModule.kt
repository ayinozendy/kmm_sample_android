package com.ayinozendy.android.poc.kmmandroidapplication.di

import android.app.Application
import com.ayinozendy.android.poc.kmm.shared.data.persistence.DatabaseFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import shared.persistence.KmmAppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideKmmDatabase(
        application: Application
    ): KmmAppDatabase {
        return DatabaseFactory(application).createDatabase()
    }
}
