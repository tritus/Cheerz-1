package com.github.colinjeremie.cheerz.framework.datasources

import com.github.colinjeremie.cheerz.framework.api.Api
import com.github.colinjeremie.data.datasources.MediaRemoteDataSource
import com.github.colinjeremie.domain.Media
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.util.*

class MediaRemoteDataSourceApiImpl(private val api: Api, private val apiKey: String) :
    MediaRemoteDataSource {

    override fun getMediaAtDate(date: Date): Deferred<Media> =
            CoroutineScope(Dispatchers.IO).async {
                api.getMedia(java.sql.Date(date.time), apiKey).await()
                        .let {
                            Media(
                                    title = it.title,
                                    explanation = it.explanation,
                                    mediaType = it.mediaType,
                                    date = it.date,
                                    url = it.url,
                                    hdUrl = it.hdUrl
                            )
                        }
            }

}