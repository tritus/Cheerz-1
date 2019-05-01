package com.github.colinjeremie.domain

import java.util.*

data class Picture(
    val date: Date,
    val explanation: String,
    val hdurl: String?,
    val mediaType: String,
    val title: String,
    val url: String
)