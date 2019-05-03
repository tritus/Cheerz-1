package com.github.colinjeremie.domain.repositories

import com.github.colinjeremie.domain.entities.Picture

interface MediaRepository {
    suspend fun getLastPictures(number: Int): List<Picture>
}