package com.github.colinjeremie.cheerz.presentation

import android.support.annotation.StringRes
import com.github.colinjeremie.cheerz.R
import com.github.colinjeremie.domain.Picture
import com.github.colinjeremie.usecases.GetPicturesUseCase
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.lang.Exception
import java.net.HttpURLConnection
import java.util.*

class MainPresenter(private val interaction: Interaction, private val useCase: GetPicturesUseCase) {

    private var getPicturesScope: Job? = null

    fun onRetrieveButtonClicked() {
        getPicturesScope?.cancel()

        interaction.onRefresh()

        getPicturesScope = CoroutineScope(Dispatchers.IO).launch {
            try {
                val pictures = useCase.getPicturesSinceDate(Date())
                withContext(Dispatchers.Main) {
                    interaction.render(pictures)
                    interaction.onRefreshSuccess()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    val errorMessage = when ((e as? HttpException)?.code() ?: -1) {
                        HttpURLConnection.HTTP_FORBIDDEN -> R.string.error_message_unauthorized
                        else -> -1
                    }
                    interaction.onRefreshFailure()
                    interaction.showErrorMessage(errorMessage)
                }
            }
            getPicturesScope?.start()
        }
    }

    interface Interaction {
        fun render(pictures: List<Picture>)
        fun onRefresh()
        fun onRefreshSuccess()
        fun onRefreshFailure()
        fun showErrorMessage(@StringRes messageRes: Int)
    }
}