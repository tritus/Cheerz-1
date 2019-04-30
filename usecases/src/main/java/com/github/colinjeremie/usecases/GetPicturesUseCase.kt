package com.github.colinjeremie.usecases

import com.github.colinjeremie.data.PicturesRepository
import com.github.colinjeremie.domain.Picture
import java.util.*

class GetPicturesUseCase(private val repository: PicturesRepository) {

    fun getPicturesSinceDate(date: Date): List<Picture> = repository.getPicturesSinceDate(date)
}