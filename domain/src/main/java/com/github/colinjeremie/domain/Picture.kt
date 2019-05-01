package com.github.colinjeremie.domain

import java.util.*

data class Picture(
        val title: String,
        val explanation: String,
        val date: Date,
        val url: String,
        val hdUrl: String?)