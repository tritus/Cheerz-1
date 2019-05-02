package com.github.colinjeremie.cheerz.framework.extensions

import com.github.colinjeremie.cheerz.framework.models.Picture
import com.github.colinjeremie.domain.Picture as PictureDomain


fun PictureDomain.toPictureAppModel() =
    Picture(
        title = this.title,
        date = this.date,
        hdUrl = this.hdUrl,
        explanation = this.explanation,
        url = this.url
    )