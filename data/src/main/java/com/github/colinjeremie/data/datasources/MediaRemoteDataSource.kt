package com.github.colinjeremie.data.datasources

import com.github.colinjeremie.domain.entities.Media
import kotlinx.coroutines.Deferred
import java.util.*

interface MediaRemoteDataSource {
    fun getMediaAtDate(date: Date): Deferred<Media>
}