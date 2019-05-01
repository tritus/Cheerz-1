package com.github.colinjeremie.cheerz.framework

import com.github.colinjeremie.cheerz.framework.api.models.MediaApiModel
import com.github.colinjeremie.cheerz.framework.extensions.areDateEquals
import com.github.colinjeremie.data.MediaSource
import com.github.colinjeremie.data.exceptions.MediaNotFoundException
import com.github.colinjeremie.domain.Media
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.util.*

class TestMediaSource(private val gson: Gson) : MediaSource {

    private val json: String by lazy {
        "[{\"date\":\"2019-04-30\",\"explanation\":\"The galaxy was never in danger. For one thing, the Triangulum galaxy (M33), pictured, is much bigger than the tiny grain of rock at the head of the meteor. For another, the galaxy is much farther away -- in this instance 3 million light years as opposed to only about 0.0003 light seconds.  Even so, the meteor's path took it angularly below the galaxy. Also the wind high in Earth's atmosphere blew the meteor's glowing evaporative molecule train away from the galaxy, in angular projection.  Still, the astrophotographer was quite lucky to capture both a meteor and a galaxy in a single exposure -- which was subsequently added to two other images of M33 to bring up the spiral galaxy's colors.  At the end, the meteor was gone in a second, but the galaxy will last billions of years.    Follow APOD on: Instagram, Facebook,  Reddit, or Twitter\",\"hdUrl\":\"https://apod.nasa.gov/apod/image/1904/M33Meteor_Chokshi_2000.jpg\",\"media_type\":\"image\",\"service_version\":\"v1\",\"title\":\"Meteor Misses Galaxy\",\"url\":\"https://apod.nasa.gov/apod/image/1904/M33Meteor_Chokshi_960.jpg\"},{\"date\":\"2019-04-29\",\"explanation\":\"Massive stars, abrasive winds, mountains of dust, and energetic light sculpt one of the largest and most picturesque regions of star formation in the Local Group of Galaxies.  Known as N11, the region is visible on the upper right of many images of its home galaxy, the Milky Way neighbor known as the Large Magellanic Clouds (LMC).  The featured image was taken for scientific purposes by the Hubble Space Telescope and reprocessed for artistry by an amateur to win a Hubble's Hidden Treasures competition.  Although the section imaged above is known as NGC 1763, the entire N11 emission nebula is second in LMC size only to the Tarantula Nebula.  Compact globules of dark dust housing emerging young stars are also visible around the image.  A new study of variable stars in the LMC with Hubble has helped to recalibrate the distance scale of the  observable universe, but resulted in a slightly different scale than found using the pervasive cosmic microwave background.   Astrophysicists: Browse 1,900+ codes in the Astrophysics Source Code Library\",\"hdUrl\":\"https://apod.nasa.gov/apod/image/1904/N11_Hubble_1989.jpg\",\"media_type\":\"image\",\"service_version\":\"v1\",\"title\":\"N11: Star Clouds of the LMC\",\"url\":\"https://apod.nasa.gov/apod/image/1904/N11_Hubble_960.jpg\"}]"
    }

    private val media: List<MediaApiModel> by lazy { gson.fromJson<List<MediaApiModel>>(json) }

    override fun getMedia(date: Date, mediaType: String): Deferred<Media> =
            CoroutineScope(Dispatchers.IO).async {
                media
                        .find { it.date.areDateEquals(date) && it.isMediaTypeEquals(mediaType) }
                        ?.let {
                            Media(
                                    title = it.title,
                                    explanation = it.explanation,
                                    mediaType = it.mediaType,
                                    date = it.date,
                                    url = it.url,
                                    hdUrl = it.hdUrl
                            )
                        }
                        ?: throw MediaNotFoundException()
            }
}