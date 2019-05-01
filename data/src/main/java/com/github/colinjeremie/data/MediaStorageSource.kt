package com.github.colinjeremie.data

import com.github.colinjeremie.domain.Media
import kotlinx.coroutines.Deferred
import java.util.*

interface MediaStorageSource {
    fun getMedia(date: Date, mediaType: String): Deferred<Media>
    fun saveMedia(media: Media)
}