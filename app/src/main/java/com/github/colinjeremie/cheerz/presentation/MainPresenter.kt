package com.github.colinjeremie.cheerz.presentation

import com.github.colinjeremie.domain.Picture
import com.github.colinjeremie.usecases.GetPicturesUseCase
import kotlinx.coroutines.*
import java.util.*

class MainPresenter(private val interaction: Interaction, private val useCase: GetPicturesUseCase) {

    private var getPicturesScope: Job? = null

    fun onRetrieveButtonClicked() {
        getPicturesScope?.cancel()

        interaction.onRefresh()

        getPicturesScope = CoroutineScope(Dispatchers.IO).launch {
            val pictures = useCase.getPicturesSinceDate(Date())

            withContext(Dispatchers.Main) {
                interaction.render(pictures)
                interaction.onRefreshDone()
            }
        }
        getPicturesScope?.start()
    }

    interface Interaction {
        fun render(pictures: List<Picture>)
        fun onRefresh()
        fun onRefreshDone()
    }
}