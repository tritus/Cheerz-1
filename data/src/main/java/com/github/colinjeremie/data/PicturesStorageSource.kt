package com.github.colinjeremie.data

import com.github.colinjeremie.domain.Picture
import kotlinx.coroutines.Deferred
import java.util.*

interface PicturesStorageSource {
    fun getPicture(date: Date): Deferred<Picture>
    fun savePicture(picture: Picture)
}