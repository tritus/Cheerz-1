package com.github.colinjeremie.usecases

import com.github.colinjeremie.data.repositories.MediaRepository

class GetPicturesUseCase(private val repository: MediaRepository) {

    suspend fun invoke(number: Int) = repository.getLastPictures(number)
}