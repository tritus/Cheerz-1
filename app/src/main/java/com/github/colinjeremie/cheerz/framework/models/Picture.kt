package com.github.colinjeremie.cheerz.framework.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Picture(
    val title: String,
    val explanation: String,
    val date: Date,
    val url: String,
    val hdUrl: String?) : Parcelable