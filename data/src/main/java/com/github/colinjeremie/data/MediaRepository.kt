package com.github.colinjeremie.data

import com.github.colinjeremie.data.exceptions.MediaNotFoundException
import com.github.colinjeremie.data.extensions.toPicture
import com.github.colinjeremie.domain.MEDIA_TYPE_IMAGE
import com.github.colinjeremie.domain.Media
import com.github.colinjeremie.domain.Picture
import java.util.*

open class MediaRepository(private val networkSource: MediaSource, private val storageSource: MediaStorageSource) {

    suspend fun getLastPictures(number: Int): List<Picture> =
        getLastMedia(number, MEDIA_TYPE_IMAGE).map {
            it.toPicture()
        }

    open fun getCurrentTime(): Date = Date()

    suspend fun getLastMedia(number: Int, mediaType: String): List<Media> {
        val todayCalendar = Calendar.getInstance().apply {
            time = getCurrentTime()
            add(Calendar.DAY_OF_MONTH, -1)
        }
        val list = mutableListOf<Media>()

        while (list.size < number) {
            val date = todayCalendar.time

            val media =
                    try {
                        getMediaFromStorageAtDate(date)
                    } catch (e: MediaNotFoundException) {
                        getMediaFromNetworkAtDate(date).also {
                            storageSource.saveMedia(it)
                        }
                    }
            if (media.mediaType == mediaType) {
                list.add(media)
            }
            todayCalendar.add(Calendar.DAY_OF_MONTH, -1)
        }
        return list
    }

    private suspend fun getMediaFromStorageAtDate(date: Date): Media =
            storageSource.getMediaAtDate(date).await()

    private suspend fun getMediaFromNetworkAtDate(date: Date): Media =
            networkSource.getMediaAtDate(date).await()
}