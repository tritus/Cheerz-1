package com.github.colinjeremie.data.datasources

import com.github.colinjeremie.domain.Media
import kotlinx.coroutines.Deferred
import java.util.*

interface MediaLocalDataSource {
    fun getMediaAtDate(date: Date): Deferred<Media>
    fun saveMedia(media: Media)
}