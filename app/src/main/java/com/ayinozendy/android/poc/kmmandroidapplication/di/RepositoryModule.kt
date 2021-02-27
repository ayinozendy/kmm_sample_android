package com.ayinozendy.android.poc.kmmandroidapplication.di

import com.ayinozendy.android.poc.kmm.shared.data.repository.playlist.PlaylistRepository
import com.ayinozendy.android.poc.kmm.shared.data.repository.playlist.PlaylistRepositoryFactory
import com.ayinozendy.android.poc.kmm.shared.data.repository.video.VideoRepository
import com.ayinozendy.android.poc.kmm.shared.data.repository.video.VideoRepositoryFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.ktor.client.*
import shared.persistence.KmmAppDatabase

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Provides
    fun providePlaylistRepository(
        kmmAppDatabase: KmmAppDatabase
    ): PlaylistRepository {
        return PlaylistRepositoryFactory(kmmAppDatabase).create()
    }

    @Provides
    fun provideVideoRepository(
        kmmAppDatabase: KmmAppDatabase
    ): VideoRepository {
        return VideoRepositoryFactory(kmmAppDatabase).create()
    }
}
