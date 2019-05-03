package com.github.colinjeremie.domain.usecases

import com.github.colinjeremie.domain.entities.Picture
import com.github.colinjeremie.domain.repositories.MediaRepository

class GetPicturesUseCase(private val repository: MediaRepository) {

    suspend fun invoke(number: Int): List<Picture> = repository.getLastPictures(number)
}