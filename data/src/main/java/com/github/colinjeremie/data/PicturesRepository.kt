package com.github.colinjeremie.data

import com.github.colinjeremie.domain.Picture
import java.util.*

class PicturesRepository(private val picturesSource: PicturesSource) {

    suspend fun getPicturesSinceDate(date: Date): List<Picture> {
        return picturesSource.getPicturesSinceDate(date).await()
    }
}