package com.github.colinjeremie.cheerz.framework.api.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class MediaApiModel(
        @SerializedName("title")
        val title: String,
        @SerializedName("explanation")
        val explanation: String,
        @SerializedName("media_type")
        val mediaType: String,
        @SerializedName("date")
        val date: Date,
        @SerializedName("url")
        val url: String,
        @SerializedName("hdurl")
        val hdUrl: String?
)