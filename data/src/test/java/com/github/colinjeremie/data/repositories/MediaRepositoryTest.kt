package com.github.colinjeremie.data.repositories

import com.github.colinjeremie.data.datasources.MediaLocalDataSourceInMemoryImplTest
import com.github.colinjeremie.data.datasources.MediaRemoteDataSourceImplTest
import com.github.colinjeremie.domain.entities.MEDIA_TYPE_IMAGE
import com.github.colinjeremie.domain.entities.Media
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito.*
import java.util.*

class MediaRepositoryTest {

    val media1: Media by lazy {
        Media(
            title = "title1",
            explanation = "explanation1",
            mediaType = MEDIA_TYPE_IMAGE,
            date = Date(),
            url = "url1",
            hdUrl = "hdurl1"
        )
    }

    val media2: Media by lazy {
        Media(
            title = "title2",
            explanation = "explanation2",
            mediaType = MEDIA_TYPE_IMAGE,
            date = Calendar.getInstance().run {
                time = media1.date
                add(Calendar.DAY_OF_MONTH, -1)
                time
            },
            url = "url2",
            hdUrl = "hdurl2"
        )
    }

    val media3: Media by lazy {
        Media(
            title = "title3",
            explanation = "explanation3",
            mediaType = MEDIA_TYPE_IMAGE,
            date = Calendar.getInstance().run {
                time = media2.date
                add(Calendar.DAY_OF_MONTH, -1)
                time
            }, url = "url3",
            hdUrl = "hdurl3"
        )
    }

    val media: List<Media> by lazy {
        mutableListOf<Media>().apply {
            add(media1)
            add(media2)
            add(media3)
        }
    }

    @Test
    fun `should save all pictures in storage`() {
        // Given
        val storageSource = MediaLocalDataSourceInMemoryImplTest()
        val repository = spy(
            MediaRepositoryImpl(
                MediaRemoteDataSourceImplTest(media),
                storageSource
            )
        )
        val currentTime = Calendar.getInstance().run {
            time = media1.date
            add(Calendar.DAY_OF_MONTH, 1)
            time
        }
        `when`(repository.getCurrentTime()).thenReturn(currentTime)

        // When
        runBlocking {
            repository.getLastPictures(media.size)

            // Then
            media.forEach {
                storageSource.getMediaAtDate(it.date).await()
            }
        }
    }

    @Test
    fun `should retrieve all pictures from storage and none from network`() {
        // Given
        val numberOfPictureToGet = media.size
        val storageSource = spy(MediaLocalDataSourceInMemoryImplTest(media.toMutableList()))
        val networkSource = spy(MediaRemoteDataSourceImplTest())
        val repository = spy(MediaRepositoryImpl(networkSource, storageSource))
        val currentTime = Calendar.getInstance().run {
            time = media1.date
            add(Calendar.DAY_OF_MONTH, 1)
            time
        }
        `when`(repository.getCurrentTime()).thenReturn(currentTime)

        // When
        runBlocking {
            repository.getLastPictures(numberOfPictureToGet)

            // Then
            media.forEach {
                verify(storageSource, times(1)).getMediaAtDate(it.date)
                verify(networkSource, never()).getMediaAtDate(it.date)
            }
        }
    }

    @Test
    fun `should save missing storage pictures with network calls`() {
        // Given
        val mediaFromNetwork = media.minBy { it.date }!!.let {
            val date = Calendar.getInstance().run {
                time = it.date
                add(Calendar.DAY_OF_MONTH, -1)
                time
            }
            it.copy(date = date)

        }
        val storageSource = spy(MediaLocalDataSourceInMemoryImplTest(media.toMutableList()))
        val networkSource = spy(
            MediaRemoteDataSourceImplTest(
                listOf(
                    mediaFromNetwork
                )
            )
        )
        val repository = spy(MediaRepositoryImpl(networkSource, storageSource))
        val currentTime = Calendar.getInstance().run {
            time = media1.date
            add(Calendar.DAY_OF_MONTH, 1)
            time
        }
        `when`(repository.getCurrentTime()).thenReturn(currentTime)

        // When
        runBlocking {
            repository.getLastPictures(media.size + 1)

            // Then
            media.forEach {
                verify(storageSource, times(1)).getMediaAtDate(it.date)
            }
            verify(networkSource, times(1)).getMediaAtDate(mediaFromNetwork.date)

            assertEquals(storageSource.getMediaAtDate(mediaFromNetwork.date).await(), mediaFromNetwork)
        }
    }
}