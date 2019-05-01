package com.github.colinjeremie.data

import com.github.colinjeremie.data.exceptions.PictureNotFoundException
import com.github.colinjeremie.domain.Picture
import java.util.*

class PicturesRepository(private val networkSource: PicturesSource, private val storageSource: PicturesStorageSource) {

    suspend fun getPicturesBetweenDates(fromDate: Date, toDate: Date): List<Picture> {
        val fromCalendar = Calendar.getInstance().apply { time = fromDate }
        val toDateInMillis = toDate.time
        val list = mutableListOf<Picture>()

        while (fromCalendar.timeInMillis < toDateInMillis) {
            val date = fromCalendar.time

            val picture =
                    try {
                        storageSource.getPicture(date).await()
                    } catch (e: PictureNotFoundException) {
                        networkSource.getPicture(date).await().also {
                            storageSource.savePicture(it)
                        }
                    }
            list.add(picture)
            fromCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        return list
    }
}