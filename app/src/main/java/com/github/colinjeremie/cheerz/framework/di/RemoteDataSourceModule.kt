package com.github.colinjeremie.cheerz.framework.di

import com.github.colinjeremie.cheerz.framework.api.Api
import com.github.colinjeremie.cheerz.framework.datasources.MediaRemoteDataSourceApiImpl
import com.github.colinjeremie.data.datasources.MediaRemoteDataSource
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val API_KEY = "VzjaTVf9solTLdPw2GnHGI18AwkA9V03GeoZR3uZ"
private const val BASE_URL = "https://api.nasa.gov"

val remoteDataSourceModule: Module by lazy {

    module {
        single<MediaRemoteDataSource> {
            MediaRemoteDataSourceApiImpl(
                apiKey = API_KEY,
                api = get()
            )
        }
        single {
            val okHttpClient = OkHttpClient().newBuilder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build()

            val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .addConverterFactory(GsonConverterFactory.create(get()))
                    .build()

            retrofit.create(Api::class.java)
        }
    }
}