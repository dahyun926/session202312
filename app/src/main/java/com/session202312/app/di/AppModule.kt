package com.session202312.app.di

import com.session202312.app.db.PokemonDB
import com.session202312.app.db.PokemonDao
import com.session202312.app.network.ApiService
import com.session202312.app.network.RetrofitService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return RetrofitService.apiService
    }

    @Provides
    @Singleton
    fun providePokemonDao(): PokemonDao {
        return PokemonDB.getInstance().pokemonDao()
    }
}