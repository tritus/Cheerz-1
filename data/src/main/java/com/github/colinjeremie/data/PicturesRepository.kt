package com.github.colinjeremie.data

import com.github.colinjeremie.data.exceptions.PictureNotFoundException
import com.github.colinjeremie.domain.Picture
import java.util.*

class PicturesRepository(private val networkSource: PicturesSource, private val storageSource: PicturesStorageSource) {

    suspend fun getLastPictures(number: Int): List<Picture> {
        val todayCalendar = Calendar.getInstance().apply { time = Date() }
        val list = mutableListOf<Picture>()

        while (list.size < number) {
            val date = todayCalendar.time

            val picture =
                    try {
                        storageSource.getPicture(date).await()
                    } catch (e: PictureNotFoundException) {
                        networkSource.getPicture(date).await()
                                .takeIf { it.isMedia(Picture.MEDIA_TYPE_IMAGE) }
                                ?.also {
                                    storageSource.savePicture(it)
                                }
                    }
            if (picture != null) {
                list.add(picture)
            }
            todayCalendar.add(Calendar.DAY_OF_MONTH, -1)
        }
        return list
    }
}