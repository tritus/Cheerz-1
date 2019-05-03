package com.github.colinjeremie.data.extensions

import java.util.*

fun Date.areDateEquals(date: Date): Boolean {
    val first = Calendar.getInstance().apply { time = this@areDateEquals }
    val other = Calendar.getInstance().apply { time = date }

    return first.get(Calendar.DAY_OF_MONTH) == other.get(Calendar.DAY_OF_MONTH) &&
            first.get(Calendar.MONTH) == other.get(Calendar.MONTH) &&
            first.get(Calendar.YEAR) == other.get(Calendar.YEAR)
}