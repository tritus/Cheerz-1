package com.github.colinjeremie.cheerz.framework.api

import com.github.colinjeremie.cheerz.framework.api.models.MediaApiModel
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query
import java.sql.Date

interface Api {

    @GET("planetary/apod")
    fun getMedia(@Query("date") date: Date, @Query("api_key") api_key: String): Deferred<MediaApiModel>
}