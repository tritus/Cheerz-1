package com.github.colinjeremie.domain

import java.util.*

const val MEDIA_TYPE_IMAGE = "image"
const val MEDIA_TYPE_VIDEO = "video"

data class Media(
        val title: String,
        val explanation: String,
        val mediaType: String,
        val date: Date,
        val url: String,
        val hdUrl: String?
)