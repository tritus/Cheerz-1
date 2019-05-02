package com.github.colinjeremie.cheerz.presentation.fullscreen

import android.widget.ImageView
import androidx.annotation.StringRes
import com.github.colinjeremie.cheerz.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class FullScreenPictureDialogFragmentPresenter(private val interaction: Interaction) {

    fun loadPicture(pictureUrl: String) {
        interaction.loading()

        Picasso.get().load(pictureUrl).into(interaction.getPictureTargetView(), object: Callback {
            override fun onSuccess() {
                interaction.loadingSuccess()
            }

            override fun onError(e: Exception?) {
                interaction.loadingFailure()
                interaction.showErrorMessage(R.string.error_message_generic)
            }
        })
    }

    interface Interaction {
        fun getPictureTargetView(): ImageView
        fun loadingSuccess()
        fun loadingFailure()
        fun showErrorMessage(@StringRes errorMessage: Int)
        fun loading()
    }
}