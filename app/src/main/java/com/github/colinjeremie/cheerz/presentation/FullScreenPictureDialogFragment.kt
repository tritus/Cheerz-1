package com.github.colinjeremie.cheerz.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.github.colinjeremie.cheerz.R

class FullScreenPictureDialogFragment : DialogFragment(), FullScreenPictureDialogFragmentPresenter.Interaction {

    companion object {
        private const val EXTRA_HD_URL = "fullScreenPictureDialogFragment.extra_hd_url"
        private const val TAG = "FullScreenPictureDialogFragment"

        fun create(url: String, fragmentManager: FragmentManager) {
            val fragment = FullScreenPictureDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_HD_URL, url)
                }
            }

            fragmentManager.beginTransaction().run {
                fragmentManager.findFragmentByTag(TAG)?.let { previousFragment ->
                    remove(previousFragment)
                }
                add(fragment, TAG)
                commit()
            }
        }
    }

    private lateinit var pictureUrl: String
    private lateinit var progressView: ProgressBar
    private lateinit var imageView: ImageView

    private val presenter by lazy {
        FullScreenPictureDialogFragmentPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pictureUrl = savedInstanceState?.getString(EXTRA_HD_URL) ?: arguments?.getString(EXTRA_HD_URL) ?: throw IllegalArgumentException("Missing picture url!")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fullscreen_picture_dialog_fragment_layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressView = view.findViewById(R.id.loading_view)
        imageView = view.findViewById(R.id.fullscreen_picture_image_view)

        presenter.loadPicture(pictureUrl)
    }

    override fun loading() {
        imageView.visibility = View.GONE
        progressView.visibility = View.VISIBLE
    }

    override fun loadingFailure() {
        progressView.visibility = View.GONE
    }

    override fun loadingSuccess() {
        imageView.visibility = View.VISIBLE
        progressView.visibility = View.GONE
    }

    override fun showErrorMessage(@StringRes errorMessage: Int) {
        Toast.makeText(imageView.context, errorMessage, Toast.LENGTH_LONG).show()
    }

    override fun getPictureTargetView(): ImageView = imageView
}