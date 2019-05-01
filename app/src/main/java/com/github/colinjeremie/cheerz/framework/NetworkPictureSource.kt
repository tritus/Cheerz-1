package com.github.colinjeremie.cheerz.framework

import com.github.colinjeremie.cheerz.framework.api.Api
import com.github.colinjeremie.data.PicturesSource
import com.github.colinjeremie.domain.Picture
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class NetworkPictureSource(private val gson: Gson) : PicturesSource {

    companion object {
        private const val API_KEY = "VzjaTVf9solTLdPw2GnHGI18AwkA9V03GeoZR3uZ"
        private const val BASE_URL = "https://api.nasa.gov"
    }

    private val api: Api by lazy {
        val okHttpClient = OkHttpClient().newBuilder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        retrofit.create(Api::class.java)
    }

    override fun getPicturesSinceDate(date: Date): Deferred<List<Picture>> = api.getPictures(gson.toJson(date), API_KEY)
}