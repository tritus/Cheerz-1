package com.github.colinjeremie.data

import com.github.colinjeremie.data.exceptions.MediaNotFoundException
import com.github.colinjeremie.domain.Media
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.util.*

open class InMemoryMediaStorageSourceTest(private val media: MutableList<Media> = mutableListOf()) : MediaStorageSource {

    override fun getMediaAtDate(date: Date): Deferred<Media> =
        CoroutineScope(Dispatchers.Default).async {
            media.find { it.date.areDateEquals(date) }
                ?: throw MediaNotFoundException()
        }

    override fun saveMedia(media: Media) {
        this.media.add(media)
    }
}