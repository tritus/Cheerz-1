package com.github.colinjeremie.data

import com.github.colinjeremie.domain.Picture
import java.util.*

interface PicturesSource {
    fun getPicturesSinceDate(date: Date): List<Picture>
}