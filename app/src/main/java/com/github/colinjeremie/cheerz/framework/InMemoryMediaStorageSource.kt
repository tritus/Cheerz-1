package com.github.colinjeremie.cheerz.framework

import com.github.colinjeremie.cheerz.framework.extensions.areDateEquals
import com.github.colinjeremie.data.MediaStorageSource
import com.github.colinjeremie.data.exceptions.MediaNotFoundException
import com.github.colinjeremie.domain.Media
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.util.*

class InMemoryMediaStorageSource : MediaStorageSource {
    private val media: MutableList<Media> by lazy {
        mutableListOf<Media>()
    }

    override fun getMediaAtDate(date: Date): Deferred<Media> =
            CoroutineScope(Dispatchers.Default).async {
                media.find { it.date.areDateEquals(date) }
                        ?: throw MediaNotFoundException()
            }


    override fun saveMedia(media: Media) {
        this.media.add(media)
    }
}