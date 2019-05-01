package com.github.colinjeremie.cheerz.framework

import com.github.colinjeremie.cheerz.framework.extensions.areDateEquals
import com.github.colinjeremie.data.PicturesStorageSource
import com.github.colinjeremie.data.exceptions.PictureNotFoundException
import com.github.colinjeremie.domain.Picture
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.util.*

class InMemoryStoragePictureSource : PicturesStorageSource {
    private val pictures: MutableList<Picture> by lazy {
        mutableListOf<Picture>()
    }

    override fun getPicture(date: Date): Deferred<Picture> =
            CoroutineScope(Dispatchers.IO).async {
                pictures.find { it.date.areDateEquals(date) } ?: throw PictureNotFoundException()
            }

    override fun savePicture(picture: Picture) {
        pictures.add(picture)
    }
}