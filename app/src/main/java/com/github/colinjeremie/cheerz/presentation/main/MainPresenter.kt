package com.github.colinjeremie.cheerz.presentation.main

import android.support.annotation.StringRes
import com.github.colinjeremie.cheerz.R
import com.github.colinjeremie.domain.Picture
import com.github.colinjeremie.usecases.GetPicturesUseCase
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.net.HttpURLConnection

class MainPresenter(private val interaction: Interaction, private val useCase: GetPicturesUseCase) {

    private var getPicturesScope: Job? = null

    private fun onError(e: Exception) {
        val errorMessage = when ((e as? HttpException)?.code() ?: -1) {
            HttpURLConnection.HTTP_FORBIDDEN -> R.string.error_message_unauthorized
            HttpURLConnection.HTTP_INTERNAL_ERROR -> R.string.error_message_internal_server
            else -> -1
        }
        interaction.onRefreshFailure()
        interaction.showErrorMessage(errorMessage)
    }

    fun onRetrieveButtonClicked() {
        getPicturesScope?.cancel()

        val numberOfPicturesToRetrieve = interaction.getNumberOfPicturesToRetrieve()

        interaction.onRefresh()

        getPicturesScope = CoroutineScope(Dispatchers.IO).launch {
            try {
                val pictures = useCase.invoke(numberOfPicturesToRetrieve)

                withContext(Dispatchers.Main) {
                    interaction.render(pictures)
                    interaction.onRefreshSuccess()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError(e)
                }
            }
            getPicturesScope?.start()
        }
    }

    fun onRetrieveNewPicturesButtonClicked() {
        interaction.resetView()
    }

    fun onPictureLongClicked(picture: Picture) {
        val hdPictureUrl = picture.hdUrl

        if (hdPictureUrl?.isNotEmpty() == true) {
            interaction.displayFullScreenPicture(hdPictureUrl)
        } else {
            interaction.showErrorMessage(R.string.error_message_no_hd_picture_available)
        }
    }

    fun onDestroy() {
        getPicturesScope?.cancel()
    }

    interface Interaction {
        fun render(pictures: List<Picture>)
        fun onRefresh()
        fun onRefreshSuccess()
        fun onRefreshFailure()
        fun showErrorMessage(@StringRes messageRes: Int)
        fun getNumberOfPicturesToRetrieve(): Int
        fun resetView()
        fun displayFullScreenPicture(pictureHdUrl: String)
    }
}