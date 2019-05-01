package com.github.colinjeremie.usecases

import com.github.colinjeremie.data.PicturesRepository
import com.github.colinjeremie.domain.Picture
import java.util.*

class GetPicturesUseCase(private val repository: PicturesRepository) {

    suspend fun getPicturesBetweenDates(fromDate: Date, toDate: Date): List<Picture> = repository.getPicturesBetweenDates(fromDate, toDate)
}