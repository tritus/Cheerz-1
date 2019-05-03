package com.github.colinjeremie.data.datasources

import com.github.colinjeremie.data.exceptions.MediaNotFoundException
import com.github.colinjeremie.data.extensions.areDateEquals
import com.github.colinjeremie.domain.entities.Media
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.util.*

open class MediaRemoteDataSourceImplTest(private val media: List<Media> = emptyList()) :
    MediaRemoteDataSource {

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