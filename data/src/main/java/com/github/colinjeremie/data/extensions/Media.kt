package com.github.colinjeremie.data.extensions

import com.github.colinjeremie.domain.Media
import com.github.colinjeremie.domain.Picture

fun Media.toPicture(): Picture =
        Picture(
                title = this.title,
                explanation = this.explanation,
                url = this.url,
                hdUrl = this.hdUrl,
                date = this.date
        )