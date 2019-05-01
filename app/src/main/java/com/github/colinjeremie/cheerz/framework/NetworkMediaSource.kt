package com.github.colinjeremie.cheerz.framework

import com.github.colinjeremie.cheerz.framework.api.Api
import com.github.colinjeremie.data.MediaSource
import com.github.colinjeremie.data.exceptions.MediaNotFoundException
import com.github.colinjeremie.domain.Media
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class NetworkMediaSource(private val gson: Gson) : MediaSource {

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

    override fun getMedia(date: Date, mediaType: String): Deferred<Media> =
            CoroutineScope(Dispatchers.IO).async {
                api.getMedia(java.sql.Date(date.time), API_KEY).await()
                        .takeIf { it.isMediaTypeEquals(mediaType) }
                        ?.let {
                            Media(
                                    title = it.title,
                                    explanation = it.explanation,
                                    mediaType = it.mediaType,
                                    date = it.date,
                                    url = it.url,
                                    hdUrl = it.hdUrl
                            )
                        }
                        ?: throw MediaNotFoundException()
            }

}