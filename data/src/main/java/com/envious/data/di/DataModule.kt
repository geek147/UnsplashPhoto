package com.envious.data.di

import com.envious.data.BuildConfig
import com.envious.data.remote.GetCollectionUseCaseImpl
import com.envious.data.remote.PhotoApiService
import com.envious.data.remote.SearchPhotoUseCaseImpl
import com.envious.data.repository.PhotoRepositoryImpl
import com.envious.domain.repository.PhotoRepository
import com.envious.domain.usecase.GetCollectionsUseCase
import com.envious.domain.usecase.SearchPhotoUseCase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseUrl

@Module
@ExperimentalCoroutinesApi
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    fun provideApiService(retrofit: Retrofit): PhotoApiService = PhotoApiService(retrofit)

    @Provides
    fun provideMoshi(): Moshi {
        return Moshi
            .Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    fun provideRetrofit(
        @BaseUrl baseUrl: String,
        moshi: Moshi,
        client: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(baseUrl)
            .build()
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(
                HttpLoggingInterceptor()
                    .apply { level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE },
            )
            .addInterceptor(HeaderInterceptor())
            .build()
    }

    class HeaderInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response = chain.run {
            proceed(
                request()
                    .newBuilder()
                    .addHeader("Accept-Version", "v1")
                    .addHeader("Authorization", "Client-ID " + BuildConfig.UNSPLASH_ACCESS_KEY)
                    .build()
            )
        }
    }

    @Provides
    @BaseUrl
    fun provideBaseUrl(): String = BuildConfig.BASE_URL

    @Provides
    fun providePhotoGallery(repository: PhotoRepositoryImpl):
        PhotoRepository = repository

    @Provides
    fun provideGetCollectionUseCase(getCollectionUseCase: GetCollectionUseCaseImpl):
        GetCollectionsUseCase = getCollectionUseCase

    @Provides
    fun provideSearchPhotoUseCase(searchPhotoUseCase: SearchPhotoUseCaseImpl):
        SearchPhotoUseCase = searchPhotoUseCase
}
