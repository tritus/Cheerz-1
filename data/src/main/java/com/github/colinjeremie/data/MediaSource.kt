package com.github.colinjeremie.data

import com.github.colinjeremie.domain.Media
import kotlinx.coroutines.Deferred
import java.util.*

interface MediaSource {
    fun getMediaAtDate(date: Date): Deferred<Media>
}