package com.github.colinjeremie.data

import com.github.colinjeremie.data.exceptions.MediaNotFoundException
import com.github.colinjeremie.domain.Media
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.util.*

open class MediaSourceTest(val media: List<Media> = emptyList()) : MediaSource {

    override fun getMediaAtDate(date: Date): Deferred<Media> =
        CoroutineScope(Dispatchers.Default).async {
            media
                .find { it.date.areDateEquals(date) }
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