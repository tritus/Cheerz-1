package com.github.colinjeremie.cheerz.framework

import com.github.colinjeremie.data.exceptions.MediaNotFoundException
import com.github.colinjeremie.domain.MEDIA_TYPE_IMAGE
import com.github.colinjeremie.domain.Media
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.util.*

class InMemoryMediaStorageSourceTest {

    @Test(expected = MediaNotFoundException::class)
    fun `should throw media not found when list is empty`() {
        // Given
        val source = InMemoryMediaStorageSource()
        val date = Date()

        // When
        runBlocking {
            source.getMediaAtDate(date).await()
        }
    }

    @Test(expected = MediaNotFoundException::class)
    fun `should throw media not found when item not found`() {
        // Give
        val source = InMemoryMediaStorageSource()
        val date = Date()
        val media = Media("title", "explanation", MEDIA_TYPE_IMAGE, date, "url", "hdurl")
        source.saveMedia(media)

        runBlocking {
            // When
            val dateToFind = Calendar.getInstance().apply {
                time = date
                add(Calendar.DAY_OF_MONTH, 1)
            }
            source.getMediaAtDate(dateToFind.time).await()
        }
    }

    @Test
    fun `should get saved media`() {
        // Give
        val source = InMemoryMediaStorageSource()
        val date = Date()
        val media = Media("title", "explanation", MEDIA_TYPE_IMAGE, date, "url", "hdurl")
        source.saveMedia(media)

        runBlocking {
            // When
            val mediaFetched = source.getMediaAtDate(date).await()

            // Then
            assertEquals(media, mediaFetched)
        }
    }
}