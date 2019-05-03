package com.github.colinjeremie.data.extensions

import com.github.colinjeremie.domain.entities.Media
import com.github.colinjeremie.domain.entities.Picture

fun Media.toPicture(): Picture =
    Picture(
        title = this.title,
        explanation = this.explanation,
        url = this.url,
        hdUrl = this.hdUrl,
        date = this.date
    )