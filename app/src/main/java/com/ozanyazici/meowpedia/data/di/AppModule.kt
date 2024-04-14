package com.ozanyazici.meowpedia.data.di

import com.ozanyazici.meowpedia.data.remote.CatAPI
import com.ozanyazici.meowpedia.data.repository.CatRepositoryImpl
import com.ozanyazici.meowpedia.domain.repository.CatRepository
import com.ozanyazici.meowpedia.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCatApi(): CatAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CatAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideBreedRepository(api: CatAPI): CatRepository {
        return CatRepositoryImpl(api = api)
    }
}