package com.github.colinjeremie.cheerz.framework.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class Media(
        @SerializedName("date")
        val date: Date,
        @SerializedName("explanation")
        val explanation: String,
        @SerializedName("hdUrl")
        val hdUrl: String?,
        @SerializedName("media_type")
        val mediaType: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("url")
        val url: String
)