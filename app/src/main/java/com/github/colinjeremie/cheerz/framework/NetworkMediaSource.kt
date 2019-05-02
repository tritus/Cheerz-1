package com.github.colinjeremie.cheerz.framework

import com.github.colinjeremie.cheerz.framework.api.Api
import com.github.colinjeremie.data.MediaSource
import com.github.colinjeremie.domain.Media
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.util.*

class NetworkMediaSource(private val api: Api, private val apiKey: String) : MediaSource {

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