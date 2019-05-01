package com.github.colinjeremie.usecases

import com.github.colinjeremie.data.PicturesRepository
import com.github.colinjeremie.domain.Picture

class GetPicturesUseCase(private val repository: PicturesRepository) {

    suspend fun getLastPictures(number: Int): List<Picture> = repository.getLastPictures(number)
}