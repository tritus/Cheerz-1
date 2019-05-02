package com.github.colinjeremie.cheerz.presentation.main

import android.support.annotation.StringRes
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import com.github.colinjeremie.cheerz.R
import com.github.colinjeremie.domain.Picture
import com.github.colinjeremie.usecases.GetPicturesUseCase
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.net.HttpURLConnection

class MainPresenter(private val useCase: GetPicturesUseCase) {

    private var getPicturesScope: Job? = null
    var interaction: Interaction? = null

    private fun onError(e: Exception) {
        val errorMessage = when ((e as? HttpException)?.code() ?: -1) {
            HttpURLConnection.HTTP_FORBIDDEN -> R.string.error_message_unauthorized
            HttpURLConnection.HTTP_INTERNAL_ERROR -> R.string.error_message_internal_server
            else -> -1
        }
        interaction?.onRefreshFailure()
        interaction?.showErrorMessage(errorMessage)
    }

    fun retrievePictures(numberOfPicturesToRetrieve: Int) {
        getPicturesScope?.cancel()
        interaction?.onRefresh()

        getPicturesScope = CoroutineScope(Dispatchers.IO).launch {
            try {
                val pictures = useCase.invoke(numberOfPicturesToRetrieve)

                withContext(Dispatchers.Main) {
                    interaction?.render(pictures)
                    interaction?.onRefreshSuccess()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError(e)
                }
            }
            getPicturesScope?.start()
        }
    }

    fun onPictureLongClicked(picture: Picture) {
        val hdPictureUrl = picture.hdUrl

        if (hdPictureUrl?.isNotEmpty() == true) {
            interaction?.displayFullScreenPicture(hdPictureUrl)
        } else {
            interaction?.showErrorMessage(R.string.error_message_no_hd_picture_available)
        }
    }

    fun subscribe(interaction: Interaction) {
        this.interaction = interaction
    }

    fun unsubscribe() {
        this.interaction = null
        getPicturesScope?.cancel()
    }

    fun onCreateOptionsMenu(menuInflater: MenuInflater, menu: Menu?, supportActionBar: ActionBar?) {
        menuInflater.inflate(R.menu.main_menu, menu)
    }

    fun onOptionsItemSelected(item: MenuItem?): Boolean =
            when (item?.itemId) {
                R.id.action_retrieve_last_pictures -> {
                    interaction?.showRetrieveLastPicturesDialog()
                    true
                }
                else -> false
            }

    fun onRetrieveLastPicturesDialogButtonClicked(numberString: String) {
        try {
            retrievePictures(numberString.toInt())
        } catch (e: java.lang.NumberFormatException) {
            interaction?.showErrorMessage(R.string.error_message_enter_a_positive_number)
        }
    }


    interface Interaction {
        fun render(pictures: List<Picture>)
        fun onRefresh()
        fun onRefreshSuccess()
        fun onRefreshFailure()
        fun showErrorMessage(@StringRes messageRes: Int)
        fun resetView()
        fun displayFullScreenPicture(pictureHdUrl: String)
        fun showRetrieveLastPicturesDialog()
    }
}