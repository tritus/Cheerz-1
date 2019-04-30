package com.github.colinjeremie.data

import com.github.colinjeremie.domain.Picture
import kotlinx.coroutines.Deferred
import java.util.*

interface PicturesSource {
    fun getPicturesSinceDate(date: Date): Deferred<List<Picture>>
}