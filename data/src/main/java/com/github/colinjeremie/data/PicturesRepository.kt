package com.github.colinjeremie.data

import com.github.colinjeremie.domain.Picture
import java.util.*

class PicturesRepository(private val picturesSource: PicturesSource) {

    suspend fun getPicturesBetweenDates(fromDate: Date, toDate: Date): List<Picture> {
        return picturesSource.getPicturesBetweenDates(fromDate, toDate).await()
    }
}