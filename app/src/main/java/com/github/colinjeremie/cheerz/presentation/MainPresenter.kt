package com.github.colinjeremie.cheerz.presentation

import com.github.colinjeremie.domain.Picture
import com.github.colinjeremie.usecases.GetPicturesUseCase
import java.util.*

class MainPresenter(private val interaction: Interaction, private val useCase: GetPicturesUseCase) {


    fun onRetrieveButtonClicked() {
        interaction.onRefresh()
        val pictures = useCase.getPicturesSinceDate(Date())

        interaction.render(pictures)
        interaction.onRefreshDone()
    }

    interface Interaction {
        fun render(pictures: List<Picture>)
        fun onRefresh()
        fun onRefreshDone()
    }
}