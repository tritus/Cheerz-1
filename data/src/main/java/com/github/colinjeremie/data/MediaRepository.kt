package com.github.colinjeremie.data

import com.github.colinjeremie.data.exceptions.MediaNotFoundException
import com.github.colinjeremie.domain.Media
import java.util.*

class MediaRepository(private val networkSource: MediaSource, private val storageSource: MediaStorageSource) {

    suspend fun getLastMedia(number: Int, mediaType: String): List<Media> {
        val todayCalendar = Calendar.getInstance().apply {
            time = Date()
            add(Calendar.DAY_OF_MONTH, -1)
        }
        val list = mutableListOf<Media>()

        while (list.size < number) {
            val date = todayCalendar.time

            val media =
                    try {
                        getMediaFromStorage(date, mediaType)
                    } catch (e: MediaNotFoundException) {
                        getMediaFromNetwork(date, mediaType)?.also {
                            storageSource.saveMedia(it)
                        }
                    }
            if (media != null) {
                list.add(media)
            }
            todayCalendar.add(Calendar.DAY_OF_MONTH, -1)
        }
        return list
    }

    private suspend fun getMediaFromStorage(date: Date, mediaType: String): Media =
            storageSource.getMedia(date, mediaType).await()

    private suspend fun getMediaFromNetwork(date: Date, mediaType: String): Media? =
            try {
                networkSource.getMedia(date, mediaType).await()
            } catch (e: MediaNotFoundException) {
                null
            }
}