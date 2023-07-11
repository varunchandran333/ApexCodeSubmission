package com.apex.codeassesment.di

import android.content.Context
import com.apex.codeassesment.data.Constants.BASE_URL
import com.apex.codeassesment.data.Constants.CACHE_SIZE_BYTES
import com.apex.codeassesment.data.Constants.CONNECTION_TIMEOUT
import com.apex.codeassesment.data.Constants.READ_TIMEOUT
import com.apex.codeassesment.data.Constants.WRITE_TIMEOUT
import com.apex.codeassesment.data.remote.APIs
import com.apex.codeassesment.data.remote.RemoteDataSource
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        headerInterceptor: Interceptor
    ): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient().newBuilder()
        okHttpClientBuilder.connectTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
        okHttpClientBuilder.writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
        okHttpClientBuilder.addInterceptor(headerInterceptor)
        return okHttpClientBuilder.build()
    }


    @Provides
    @Singleton
    fun provideHeaderInterceptor(): Interceptor {
        return Interceptor {
            val requestBuilder = it.request().newBuilder()
            it.proceed(requestBuilder.build())
        }
    }


    @Provides
    @Singleton
    internal fun provideCache(context: Context): Cache {
        val httpCacheDirectory = File(context.cacheDir.absolutePath, "HttpCache")
        return Cache(httpCacheDirectory, CACHE_SIZE_BYTES)
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): APIs {
        return retrofit.create(APIs::class.java)
    }

    @Provides
    fun provideRemoteDataSource(): RemoteDataSource = RemoteDataSource(
        provideApi(provideRetrofit(provideOkHttpClient(provideHeaderInterceptor()))))
}
