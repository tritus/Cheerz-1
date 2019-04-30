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

    private val api: Api by lazy {
        val okHttpClient = OkHttpClient().newBuilder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.nasa.gov")
            .client(okHttpClient)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        retrofit.create(Api::class.java)
    }

    override fun getPicturesSinceDate(date: Date): Deferred<List<Picture>> = api.getPictures(gson.toJson(date))
}