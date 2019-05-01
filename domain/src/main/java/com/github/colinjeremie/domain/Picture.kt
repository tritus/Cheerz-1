package com.github.colinjeremie.domain

import java.util.*

data class Picture(
    val date: Date,
    val explanation: String,
    val hdurl: String?,
    val media_type: String,
    val title: String,
    val url: String
) {
    companion object {
        const val MEDIA_TYPE_IMAGE = "image"
    }

    fun isMedia(type: String): Boolean = media_type == type
}