package com.github.colinjeremie.usecases

import com.github.colinjeremie.data.MediaRepository
import com.github.colinjeremie.data.extensions.toPicture
import com.github.colinjeremie.domain.MEDIA_TYPE_IMAGE
import com.github.colinjeremie.domain.Picture

class GetPicturesUseCase(private val repository: MediaRepository) {

    suspend fun getLastPictures(number: Int): List<Picture> =
            repository.getLastMedia(number, MEDIA_TYPE_IMAGE).map {
                it.toPicture()
            }
}